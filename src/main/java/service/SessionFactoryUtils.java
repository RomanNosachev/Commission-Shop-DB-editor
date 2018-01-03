package service;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtils 
{      
    private static Configuration cfg = new Configuration().configure();
    
    public static synchronized SessionFactory getFactory(String login, String password) throws HibernateException
    {
        cfg.getProperties().setProperty("hibernate.connection.username", login);
        cfg.getProperties().setProperty("hibernate.connection.password", password);
        
        return cfg.buildSessionFactory();            
    }
}