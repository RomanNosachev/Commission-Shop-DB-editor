package servise;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import dao.DB_Entity;

public abstract class AbstractService<T extends DB_Entity>
implements Service<T>
{        
    protected SessionFactory factory;
    protected Session session;
    
    public AbstractService()
    {
        this.factory = SessionFactoryUtils.factory;
    }
    
    public void create(T object)
    {
        connect();
        
        session.save(object);
        session.getTransaction().commit();
    }
    
    public void connect()
    {
        if (session == null || !session.isOpen())
            session = factory.openSession();
        
        session.beginTransaction();
    }
    
    public void disconnect()
    {
        if (session != null && session.isOpen())
        {
            session.close();
        }
    }
}
