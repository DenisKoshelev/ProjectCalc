package ru.koshelev.server;

import ru.koshelev.commons.FinalString;
import ru.koshelev.commons.Message;
import ru.koshelev.commons.entities.Demand;
import ru.koshelev.commons.entities.Purchase;
import ru.koshelev.commons.entities.Product;
import ru.koshelev.commons.entities.Sale;
import ru.koshelev.services.DemandService;
import ru.koshelev.services.ProductService;
import ru.koshelev.services.PurchaseService;
import ru.koshelev.services.SaleService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.List;

/**
 * UserHandler держит подключение с клиентом, передает сообщения между клиентом и сервером
 */
public class UserHandler {
    private ServerSocket server;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Object object;
    private Message message;
    private FinalString finalString = new FinalString();
    private ProductService productService = new ProductService();
    private PurchaseService purchaseService = new PurchaseService();
    private DemandService demandService = new DemandService();
    private SaleService saleService = new SaleService();

    protected UserHandler(){}

    /**
     * Инициализирует потоки ввода-вывода,
     * принимает сообщения от клиента и распределяет между методами, в зависимости от класса
     */
    protected UserHandler(ServerSocket server, Socket socket){
        this.server = server;
        this.socket = socket;

        in = null;
        out = null;
        try{
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            new Thread(()->{
                while (true){
                    try{
                        object = in.readObject();
                        if(object instanceof Message) {
                            message = (Message) object;
                            readMessage(message.getTopic(), message.getText(), message.getObject());
                        }
                    } catch (ClassNotFoundException | IOException e){
                        try{
                            e.printStackTrace();
                            socket.close();
                            System.out.println("Клиент отключился");
                            break;
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Обрабатывает сообщения типа Message от сервера
     * @param topic заголовок сообщения
     * @param text текст сообщения
     * @param object объект в сообщении
     */
    private void readMessage(String topic, String text, Object object){
        if(topic.equals(finalString.getNEW_PRODUCT())){
            addNewProduct(text);
        } else if(topic.equals(finalString.getPURCHASE())){
            addPurchase((Purchase) object);
        } else if (topic.equals(finalString.getDEMAND())){
            addDemand((Demand) object);
        } else if (topic.equals(finalString.getSALES_REPORT())){
            deemSalesReport(text, (Date) object);
        } else {
            sendMessage(finalString.getERROR(), finalString.getERROR_UNEXEPTED_COMMAND());
        }
    }

    /**
     * Добавляет новый товар в таблицу product, если его нет,
     * передает клиенту информацию по операции
     * @param text название продукта
     */
    private void addNewProduct(String text){
        if(!productService.isExistProductName(text)){
            productService.saveProduct(new Product(text));
            sendMessage(finalString.getSUCCESS(), "OK");
        } else {
            sendMessage(finalString.getERROR(), finalString.getERROR_PRODUCT_NAME_IS_EXIST());
        }
    }

    /**
     * Добавляет информацию о покупке товара в таблицу purchase,
     * если товар с таким именем есть в таблице product,
     * передает клиенту информацию по операции
     */
    private void addPurchase(Purchase purchase){
        String nameProduct = purchase.getProduct().getName();
        if (productService.isExistProductName(nameProduct)){
            purchase.setProduct(productService.findProductByName(nameProduct));
            purchaseService.savePurchase(purchase);
            sendMessage(finalString.getSUCCESS(), "OK");
        } else {
            sendMessage(finalString.getERROR(), finalString.getERROR_PRODUCT_NAME_IS_NOT_EXIST());
        }
    }

    /**
     * Сохраняет информацию о продаже товара в базу,
     * передает клиенту информацию по операции
     */
    private void addDemand(Demand demand){
        String nameProduct = demand.getProduct().getName();
        if (productService.isExistProductName(nameProduct)){
            Product product = productService.findProductByName(demand.getProduct().getName());
            if(saleProduct(product.getId(), demand.getAmount(), demand.getPrice(), demand.getDate())){
                demand.setProduct(product);
                demandService.saveDemand(demand);
                sendMessage(finalString.getSUCCESS(), "OK");
            } else {
                sendMessage(finalString.getERROR(), "Недостаточно продукта на продажу");
            }
        } else {
            sendMessage(finalString.getERROR(), finalString.getERROR_PRODUCT_NAME_IS_NOT_EXIST());
        }
    }

    /**
     * Если продукт есть в базе, то передает клиенту информацию о выручке по товару
     * Если продукт есть в базе, но по нему не совершались сделки - вернет клиенту 0
     * @param nameProduct название продукта
     * @param date дата(включительно) до которой формировать отчет
     */
    private void deemSalesReport(String nameProduct, Date date) {
        if (productService.isExistProductName(nameProduct)){
            Product product = productService.findProductByName(nameProduct);
            List<Sale> sales = saleService.findByIdAndDateBefore(product.getId(), date);
            int sumPrice = getSumPrice(sales);
            sendMessage(finalString.getSUCCESS(), "" + sumPrice);
        } else {
            sendMessage(finalString.getERROR(), finalString.getERROR_PRODUCT_NAME_IS_NOT_EXIST());
        }
    }

    /**
     * Считает выручку по списку
     * @param sales список сделок
     */
    protected int getSumPrice(List<Sale> sales){
        int sumPrice = 0;
        for (int i = 0; i < sales.size(); i++) {
            sumPrice += (sales.get(i).getPriceDemand()-sales.get(i).getPricePurchase()) * sales.get(i).getNumberOfSold();
        }
        return sumPrice;
    }

    /**
     * Создает список непроданных товаров.
     * Формирует информацию о сделке продажи, если хватает товара на продажу
     * @param productId id продаваемого продукта
     * @param count количество продаваемого продукта
     * @param priceDemand цена за 1 единицу товара
     * @param date дата продажи
     * @return false если не хватило товара для продажи
     */
    private boolean saleProduct(int productId, int count, int priceDemand, Date date){
        List<Purchase> purchases = purchaseService.findRemainedProduct(productId);
        if(checkRemainingProduct(purchases)>=count){
            for (int i = 0; i < purchases.size(); i++) {
                if(count>0){
                    if(purchases.get(i).getRemained()<=count){
                        transferToSale(purchases.get(i).getProduct(), purchases.get(i).getRemained(), purchases.get(i).getPrice(), priceDemand, date);
                        count = count - purchases.get(i).getRemained();
                        purchases.get(i).setRemained(0);
                        purchaseService.updatePurchase(purchases.get(i));
                    } else {
                        transferToSale(purchases.get(i).getProduct(), count, purchases.get(i).getPrice(), priceDemand, date);
                        purchases.get(i).setRemained(purchases.get(i).getRemained()-count);
                        count = 0;
                        purchaseService.updatePurchase(purchases.get(i));
                    }
                } else {
                    break;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Сохраняет информацию о продаже в таблицу sales
     * @param product товар
     * @param numberOfSold количество проданного товара
     * @param pricePurchase цена закупки
     * @param priceDemand цена продажи
     * @param date дата продажи
     */
    private void transferToSale(Product product, int numberOfSold, int pricePurchase, int priceDemand, Date date){
        saleService.saveSale(new Sale(product, numberOfSold, pricePurchase, priceDemand, date));
    }

    /**
     * Считает количество непроданных товаров
     * @param purchases список товаров
     * @return int количество непроданных товаров
     */
    protected int checkRemainingProduct(List<Purchase> purchases){
        int count = 0;
        for (int i = 0; i < purchases.size(); i++) {
            count += purchases.get(i).getRemained();
        }
        return count;
    }

    private void sendMessage(String topic, String text){
        try {
            out.writeObject(new Message(topic, text));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
