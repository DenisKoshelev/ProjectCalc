package ru.koshelev.services;

import ru.koshelev.commons.entities.Purchase;
import ru.koshelev.dao.PurchaseDao;

import java.util.List;


public class PurchaseService {
    private PurchaseDao purchaseDao = new PurchaseDao();

    public PurchaseService() {
    }

    public Purchase findPurchase(int id) {
        return purchaseDao.findById(id);
    }

    public List<Purchase> findRemainedProduct(int productId){
        return purchaseDao.findByIdAndRemainedMoreZero(productId);
    }

    public void updatePurchase(Purchase purchase){
        purchaseDao.update(purchase);
    }

    public void savePurchase(Purchase purchase) {
        purchaseDao.save(purchase);
    }

}
