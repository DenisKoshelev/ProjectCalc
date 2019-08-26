package ru.koshelev.services;

import ru.koshelev.commons.entities.Product;
import ru.koshelev.dao.ProductDao;

public class ProductService {
    private ProductDao productDao = new ProductDao();

    public ProductService() {
    }

    public Product findProductById(int id) {
        return productDao.findById(id);
    }

    public Product findProductByName(String name){
        return productDao.findByName(name);
    }

    public boolean isExistProductName(String name) {
        return productDao.findByName(name) != null;
    }

    public void saveProduct(Product product) {
        productDao.save(product);
    }
}
