package servise;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtils 
{    
    public static final SessionFactory factory = new Configuration().configure().buildSessionFactory();
    
    public static synchronized void closeFactory()
    {
        if (factory.isOpen())
            factory.close();
    }
}
