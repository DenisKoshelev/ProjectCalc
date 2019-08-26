package ru.koshelev.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.koshelev.commons.entities.Demand;
import ru.koshelev.utils.HibernateSessionFactoryUtil;

public class DemandDao {
    public Demand findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Demand.class, id);
    }

    public void save(Demand demand) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            session.save(demand);
            tx1.commit();
            session.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
