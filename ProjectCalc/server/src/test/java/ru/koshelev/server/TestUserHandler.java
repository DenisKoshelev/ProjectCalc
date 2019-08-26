package ru.koshelev.server;

import org.junit.Assert;
import org.junit.Test;
import ru.koshelev.commons.entities.Product;
import ru.koshelev.commons.entities.Purchase;
import ru.koshelev.commons.entities.Sale;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestUserHandler {
    UserHandler userHandler = new UserHandler();

    @Test
    public void testGetSumPrice(){
        Product product = new Product("nameProduct");
        Date date = new Date(System.currentTimeMillis());
        List<Sale> sales = Arrays.asList(new Sale(product, 10, 10000, 15000, date),
                                            new Sale(product, 3, 11000, 14000, date),
                                            new Sale(product, 5, 12000, 21000, date));
        int sum = userHandler.getSumPrice(sales);
        Assert.assertEquals(104000, sum);
    }

    @Test
    public void testCheckRemainingProduct(){
        Product product = new Product("nameProduct");
        Date date = new Date(System.currentTimeMillis());
        List<Purchase> purchases = Arrays.asList(new Purchase(10, 10000, date, 3),
                                                    new Purchase(10, 10000, date, 5),
                                                    new Purchase(10, 10000, date, 8),
                                                    new Purchase(10, 10000, date, 3),
                                                    new Purchase(10, 10000, date, 2));
        int sum = userHandler.checkRemainingProduct(purchases);
        Assert.assertEquals(21, sum);
    }
}
