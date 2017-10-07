package servise;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class AbstractServise<T>
implements Service<T>
{
    protected SessionFactory factory;
    protected Session session;
    
    public AbstractServise(SessionFactory factory)
    {
        this.factory = factory;
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
