package ru.koshelev.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.koshelev.commons.entities.Demand;
import ru.koshelev.commons.entities.Purchase;
import ru.koshelev.commons.entities.Product;
import ru.koshelev.commons.entities.Sale;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Purchase.class);
                configuration.addAnnotatedClass(Demand.class);
                configuration.addAnnotatedClass(Product.class);
                configuration.addAnnotatedClass(Sale.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
