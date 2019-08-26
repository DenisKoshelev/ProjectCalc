package ru.koshelev.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.koshelev.commons.entities.Product;
import ru.koshelev.utils.HibernateSessionFactoryUtil;

public class ProductDao {
    public Product findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Product.class, id);
    }

    public Product findByName(String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Product product = null;
        try {
            Query query = session.createQuery("from Product where name = :name");
            query.setParameter("name", name);
            product = (Product) query.list().get(0);
        } catch (Exception e){
            e.printStackTrace();
        }
        session.close();
        return product;
    }

    public void save(Product product) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(product);
        tx1.commit();
        session.close();
    }

}
