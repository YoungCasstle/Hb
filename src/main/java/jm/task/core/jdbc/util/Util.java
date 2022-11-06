package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static Connection conn = null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users", "root", "1337");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    ////////Hibernate

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String HOST = "jdbc:mysql://127.0.0.1:3306/users";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "1337";
    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {

            try {
                Configuration configuration2 = new Configuration()
                        .setProperty("hibernate.connection.driver_class", DRIVER)
                        .setProperty("hibernate.connection.url", HOST)
                        .setProperty("hibernate.connection.username", LOGIN)
                        .setProperty("hibernate.connection.password", PASSWORD)
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                        .addAnnotatedClass(User.class);

                ServiceRegistry sr = new StandardServiceRegistryBuilder()
                        .applySettings(configuration2.getProperties()).build();
                sessionFactory = configuration2.buildSessionFactory(sr);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void closeConnection() {
        if (sessionFactory != null)
            sessionFactory.close();
    }
}
//
