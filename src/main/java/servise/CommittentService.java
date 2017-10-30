package servise;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

import dao.Committent;

public class CommittentService 
extends AbstractService<Committent>
{
    private static final long serialVersionUID = 9017428286154265055L;

    public CommittentService(SessionFactory factory) throws RemoteException
    {
        super(factory);
    }

    public boolean remove(Serializable id) throws RemoteException
    {
        connect();
        
        Committent committent = session.get(Committent.class, id);
        
        if (committent != null)
        {
            session.getTransaction().commit();
            session.remove(committent);
            
            return true;
        }
        
        return false;
    }

    public Committent find(Serializable id) throws RemoteException
    {
        connect();
        
        Committent committent = session.find(Committent.class, id);
        session.getTransaction().commit();
        
        return committent;
    }

    public List<Committent> findAll() throws RemoteException
    {
        connect();
        
        TypedQuery<Committent> query = session.createQuery("SELECT a FROM Committent a", Committent.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }
}
