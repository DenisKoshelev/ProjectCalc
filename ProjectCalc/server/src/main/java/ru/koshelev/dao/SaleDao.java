package ru.koshelev.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.koshelev.utils.HibernateSessionFactoryUtil;
import ru.koshelev.commons.entities.Sale;

import java.util.Date;
import java.util.List;

public class SaleDao {
    public void save(Sale sale) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(sale);
        tx1.commit();
        session.close();
    }

    public List<Sale> findByIdAndDateBefore(int productId, Date date){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Sale> sales = null;
        try {
            Query query = session.createQuery("from Sale where product_id = :productId and date_demand <= :date");
            query.setParameter("productId", productId);
            query.setParameter("date", date);
            sales = query.list();
        } catch (Exception e){
            e.printStackTrace();
        }
        session.close();
        return sales;
    }
}
