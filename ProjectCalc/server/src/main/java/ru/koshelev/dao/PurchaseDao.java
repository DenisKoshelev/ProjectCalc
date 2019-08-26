package ru.koshelev.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.koshelev.commons.entities.Purchase;
import ru.koshelev.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class PurchaseDao {
    public Purchase findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Purchase.class, id);
    }

    public List<Purchase> findByIdAndRemainedMoreZero(int productId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Purchase> purchases = null;
        try {
            Query query = session.createQuery("from Purchase where product_id = :productId and remained > 0");
            query.setParameter("productId", productId);
            purchases = query.list();
        } catch (Exception e){
            e.printStackTrace();
        }
        session.close();
        return purchases;
    }

    public void update(Purchase purchase) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(purchase);
        tx1.commit();
        session.close();
    }

    public void save(Purchase purchase) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            session.save(purchase);
            tx1.commit();
            session.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
