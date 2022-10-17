package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class Util {


    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASS = "root";
    private static final String DIALECT = "org.hibernate.dialect.MySQL8Dialect";

    private static final SessionFactory sessionFactory;

    static {
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, DB_DRIVER);
        settings.put(Environment.URL, URL);
        settings.put(Environment.USER, USER);
        settings.put(Environment.PASS, PASS);
        settings.put(Environment.DIALECT, DIALECT);
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.FORMAT_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "create-drop");


        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(settings).build();

        try {
            sessionFactory = new MetadataSources(registry).addAnnotatedClass(User.class).buildMetadata().buildSessionFactory();
        }
        catch (Exception e){
            throw new ExceptionInInitializerError("Ошибка инициализации SessionFactory" + e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeSessionFactory(){
        getSessionFactory().close();
    }

}
