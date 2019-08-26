package ru.koshelev.utils;

import ru.koshelev.commons.FinalString;
import ru.koshelev.commons.entities.Demand;
import ru.koshelev.commons.entities.Purchase;
import ru.koshelev.commons.entities.Product;
import ru.koshelev.connect.Connect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

/**
 * CommandHandler обрабатывает поступающие команды, проверяет на валидность параметры
 * и передает успешные команды в Connect
 */
public class CommandHandler {
    private Connect connect;
    private FinalString finalString = new FinalString();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    protected CommandHandler(){}

    /**
     * @param connect подключение к серверу
     */
    public CommandHandler(Connect connect){
        this.connect = connect;
    }

    /**
     * processing обрабатывает строку и распределяет по методам в зависимости от названия команды
     * NEWPRODUCT <name>
     * PURCHASE <name> <amount> <price> <date>
     * DEMAND <name> <amount> <price> <date>
     * SALESREPORT <name> <date>
     * @param string строка содержащая команду с параметрами
     */
    public void processing(String string){
        List<String> strings = divide(string, " ");
        String command = strings.get(0);
        strings.remove(0);
        if (strings.size()>=1){
            if(command.equals(finalString.getNEW_PRODUCT())){
                addNewProduct(strings);
            } else if(command.equals(finalString.getPURCHASE())){
                addPurchase(strings);
            } else if (command.equals(finalString.getDEMAND())){
                addDemand(strings);
            } else if (command.equals(finalString.getSALES_REPORT())){
                salesReport(strings);
            } else {
                System.out.println(finalString.getERROR() + ": " + finalString.getERROR_UNEXEPTED_COMMAND());
            }
        } else {
            System.out.println(finalString.getERROR() + ": " + finalString.getERROR_UNEXEPTED_COMMAND());
        }
    }

    protected List<String> divide(String string, String divider){
        string = string.trim().replaceAll("[\\s]{2,}", " ");
        List<String> strings = new ArrayList<>();
        for (String part:string.split(divider)) {
            strings.add(part);
        }
        return strings;
    }

    /**
     * Проверяет параметры введенной команды NEWPRODUCT на валидность и передает в Connect
     */
    private void addNewProduct(List<String> strings){
        if(checkCountParameters(strings, 1)){
            connect.sendMessage(finalString.getNEW_PRODUCT(), strings.get(0));
        } else {
            System.out.println(finalString.getERROR() + " Неверное колличество параметров.");
        }
    }

    /**
     * Проверяет параметры введенной команды PURCHASE на валидность, создает объект purchase и передает в Connect
     */
    private void addPurchase(List<String> strings){
            if(checkValidParameters(strings, 4, 3, 1, 2)) {
                Purchase purchase = new Purchase();
                try {
                    purchase.setProduct(new Product(strings.get(0)));
                    purchase.setAmount(Integer.parseInt(strings.get(1)));
                    purchase.setPrice(Integer.parseInt(strings.get(2)));
                    purchase.setDate(sdf.parse(strings.get(3)));
                    purchase.setRemained(Integer.parseInt(strings.get(1)));
                    connect.sendMessage(finalString.getPURCHASE(), purchase);
                } catch (NumberFormatException | ParseException e) {
                    System.out.println(finalString.getERROR() + " " + finalString.getERROR_INVALID_PARAMETER());
                    e.printStackTrace();
                }
            } else {
                System.out.println(finalString.getERROR());
            }
    }

    /**
     * Проверяет параметры введенной команды DEMAND, создает объект demand на валидность и передает в Connect
     */
    private void addDemand(List<String> strings){
        if(checkValidParameters(strings, 4, 3, 1, 2)){
                Demand demand = new Demand();
                try{
                    demand.setProduct(new Product(strings.get(0)));
                    demand.setAmount(Integer.parseInt(strings.get(1)));
                    demand.setPrice(Integer.parseInt(strings.get(2)));
                    demand.setDate(sdf.parse(strings.get(3)));
                    connect.sendMessage(finalString.getDEMAND(), demand);
                } catch (NumberFormatException | ParseException e){
                    System.out.println(finalString.getERROR() + " " + finalString.getERROR_INVALID_PARAMETER());
                    e.printStackTrace();
                }
        } else {
            System.out.println(finalString.getERROR());
        }
    }

    /**
     * Проверяет параметры введенной команды SALESREPORT, создает объект sale на валидность и передает в Connect
     */
    private void salesReport(List<String> strings){
        if(checkValidParameters(strings, 2, 1)){
            if(checkValidDate(strings.get(1))){
                try {
                    connect.sendMessage(finalString.getSALES_REPORT(), strings.get(0), sdf.parse(strings.get(1)));
                } catch (ParseException e){
                    System.out.println(finalString.getERROR() + " " + finalString.getERROR_INVALID_PARAMETER());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Дата введена некорректно");
            }
        } else {
            System.out.println(finalString.getERROR() + " Неверное колличество параметров.");
        }
    }

    /**
     * checkValidParameters проверяет введенные параметры команды
     * @param strings список параметров введенной команды
     * @param countArgs количество аргументов требуемое командой
     * @param numberDate индекс параметра Дата в strings
     * @param numberInt1 индекс параметра Количество товара в strings
     * @param numberInt2 индекс параметра Цена товара в strings
     * */
    protected boolean checkValidParameters(List<String> strings, int countArgs, int numberDate, int numberInt1, int numberInt2){
        if(!checkCountParameters(strings, countArgs)) {
            return false;
        }
        if(!checkValidDate(strings.get(numberDate))) {
            return false;
        }
        if(numberInt1>=0){
            if(!(parseInt(strings.get(numberInt1))>0)) {
                return false;
            }
        }
        if(numberInt2>=0){
            if(!(parseInt(strings.get(numberInt2))>0)){
                return false;
            }
        }

        return true;
    }

    private boolean checkValidParameters(List<String> strings, int countArgs, int numberDate){
        return checkValidParameters(strings, countArgs, numberDate, -1, -1);
    }

    private int parseInt(String string){
        try{
            return Integer.parseInt(string);
        } catch (IllegalFormatException e){
            return -1;
        }
    }

    /**
     * checkValidDate проверяет, что дата введена корректно "yyyy-MM-dd"
     */
    protected boolean checkValidDate(String stringDate){
        List<String> strings = divide(stringDate, "-");
        if(strings.size()<3 || strings.size()>3){
            return false;
        }
        if(Integer.parseInt(strings.get(0))<2000){
            return false;
        }
        if(Integer.parseInt(strings.get(1))<1 || Integer.parseInt(strings.get(1))>12){
            return false;
        }
        if(Integer.parseInt(strings.get(2))<1 || Integer.parseInt(strings.get(2))>12){
            return false;
        }
        return true;
    }

    /**
     * checkCountParameters проверяет соответствует ли количество введенных параметров требованию команды
     */
    private boolean checkCountParameters(List<String> strings, int countArgs) {
        return strings.size() == countArgs;
    }
}
