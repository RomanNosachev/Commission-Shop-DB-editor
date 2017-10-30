package servise;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

import dao.Deal;

public class DealService 
extends AbstractService<Deal> 
{
    private static final long serialVersionUID = 8951949142178506414L;

    public DealService(SessionFactory factory) throws RemoteException
    {
        super(factory);
    }

    public boolean remove(Serializable id) throws RemoteException
    {
        connect();
        
        Deal deal = session.get(Deal.class, id);
        
        if (deal != null)
        {
            session.remove(deal);
            session.getTransaction().commit();
            
            return true;
        }

        return false;
    }

    public Deal find(Serializable id) throws RemoteException
    {
        connect();
        
        Deal deal = session.find(Deal.class, id);
        session.getTransaction().commit();
        
        return deal;
    }

    public List<Deal> findAll() throws RemoteException
    {
        connect();
        
        TypedQuery<Deal> query = session.createQuery("SELECT a FROM Deal a", Deal.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }    
}
