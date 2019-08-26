package ru.koshelev.commons;

/**
 * Константы использующиеся на клиенте и сервере
 */
public class FinalString {
    private final String NEW_PRODUCT = "NEWPRODUCT";
    private final String PURCHASE = "PURCHASE";
    private final String DEMAND = "DEMAND";
    private final String SALES_REPORT = "SALESREPORT";
    private final String SUCCESS = "Success";
    private final String ERROR = "ERROR";
    private final String ERROR_UNEXEPTED_COMMAND = "Комманда введена неверно или не существует";
    private final String ERROR_PRODUCT_NAME_IS_EXIST = "Название продукта уже существует";
    private final String ERROR_PRODUCT_NAME_IS_NOT_EXIST = "Продукт не существует";
    private final String ERROR_INVALID_PARAMETER = "Параметры комманды введены неверно";

    public String getNEW_PRODUCT() {
        return NEW_PRODUCT;
    }

    public String getPURCHASE() {
        return PURCHASE;
    }

    public String getDEMAND() {
        return DEMAND;
    }

    public String getSALES_REPORT() {
        return SALES_REPORT;
    }

    public String getSUCCESS() {
        return SUCCESS;
    }

    public String getERROR() {
        return ERROR;
    }

    public String getERROR_UNEXEPTED_COMMAND() {
        return ERROR_UNEXEPTED_COMMAND;
    }

    public String getERROR_PRODUCT_NAME_IS_EXIST() {
        return ERROR_PRODUCT_NAME_IS_EXIST;
    }

    public String getERROR_INVALID_PARAMETER() {
        return ERROR_INVALID_PARAMETER;
    }

    public String getERROR_PRODUCT_NAME_IS_NOT_EXIST() {
        return ERROR_PRODUCT_NAME_IS_NOT_EXIST;
    }
}
