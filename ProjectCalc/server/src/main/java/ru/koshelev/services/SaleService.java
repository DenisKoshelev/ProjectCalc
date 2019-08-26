package ru.koshelev.services;

import ru.koshelev.commons.entities.Sale;
import ru.koshelev.dao.SaleDao;

import java.util.Date;
import java.util.List;

public class SaleService {
    SaleDao saleDao = new SaleDao();

    public void saveSale(Sale sale) {
        saleDao.save(sale);
    }

    public List<Sale> findByIdAndDateBefore(int productId, Date date){
        return saleDao.findByIdAndDateBefore(productId, date);
    }
}
