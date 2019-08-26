package ru.koshelev.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestCommandHandler {
    CommandHandler commandHandler = new CommandHandler();

    @Test
    public void devide(){
        List<String> list = commandHandler.divide(" NEWPRODUCT  nameProduct", " ");
        List<String> trueList = Arrays.asList("NEWPRODUCT", "nameProduct");
        Assert.assertEquals(trueList, list);

        list = commandHandler.divide(" PURCHASE  nameProduct 1 10000  2019-01-01", " ");
        trueList = Arrays.asList("PURCHASE", "nameProduct", "1", "10000", "2019-01-01");
        Assert.assertEquals(trueList, list);

        list = commandHandler.divide("2019-01-02", "-");
        trueList = Arrays.asList("2019", "01", "02");
        Assert.assertEquals(trueList, list);
    }

    @Test
    public void testCheckValidDate(){
        boolean bool = commandHandler.checkValidDate("2019-01-02");
        Assert.assertTrue(bool);

        bool = commandHandler.checkValidDate("2019.01.02");
        Assert.assertFalse(bool);

        bool = commandHandler.checkValidDate("2019-01.02");
        Assert.assertFalse(bool);

        bool = commandHandler.checkValidDate("2019.01-02");
        Assert.assertFalse(bool);

        bool = commandHandler.checkValidDate("01-2019-02");
        Assert.assertFalse(bool);

        bool = commandHandler.checkValidDate("01-02-2019");
        Assert.assertFalse(bool);

        bool = commandHandler.checkValidDate("02-2019");
        Assert.assertFalse(bool);

        bool = commandHandler.checkValidDate("01--2019");
        Assert.assertFalse(bool);
    }

    @Test
    public void testCheckValidParameters(){
        List<String> strings = Arrays.asList("nameProduct", "5", "10000", "2019-01-01");
        boolean bool = commandHandler.checkValidParameters(strings, 4, 3, 1, 2);
        Assert.assertTrue(bool);

        strings = Arrays.asList("nameProduct", "-5", "10000", "2019-01-01");
        bool = commandHandler.checkValidParameters(strings, 4, 3, 1, 2);
        Assert.assertFalse(bool);

        strings = Arrays.asList("nameProduct", "5", "-10000", "2019-01-01");
        bool = commandHandler.checkValidParameters(strings, 4, 3, 1, 2);
        Assert.assertFalse(bool);

        strings = Arrays.asList("nameProduct", "5", "-10000", "201901-01");
        bool = commandHandler.checkValidParameters(strings, 4, 3, 1, 2);
        Assert.assertFalse(bool);

        strings = Arrays.asList("nameProduct", "name", "10000", "201901-01");
        bool = commandHandler.checkValidParameters(strings, 4, 3, 1, 2);
        Assert.assertFalse(bool);

        strings = Arrays.asList("nameProduct", "5", "name", "201901-01");
        bool = commandHandler.checkValidParameters(strings, 4, 3, 1, 2);
        Assert.assertFalse(bool);

        strings = Arrays.asList("nameProduct", "5", "10000", "name");
        bool = commandHandler.checkValidParameters(strings, 4, 3, 1, 2);
        Assert.assertFalse(bool);
    }
}
