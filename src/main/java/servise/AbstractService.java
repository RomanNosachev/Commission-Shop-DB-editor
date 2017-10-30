package servise;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class AbstractService<T>
extends UnicastRemoteObject
implements Service<T>
{    
    private static final long serialVersionUID = -2923019560480701679L;
    
    protected SessionFactory factory;
    protected Session session;
    
    public AbstractService(SessionFactory factory) throws RemoteException
    {
        this.factory = factory;
    }
    
    public void create(T object) throws RemoteException
    {
        connect();
        
        session.save(object);
        session.getTransaction().commit();
    }
    
    public void connect() throws RemoteException
    {
        if (session == null || !session.isOpen())
            session = factory.openSession();
        
        session.beginTransaction();
    }
    
    public void disconnect() throws RemoteException
    {
        if (session != null && session.isOpen())
        {
            session.close();
        }
    }
}
