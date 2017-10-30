package servise;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

import dao.SocialStatus;

public class SocialStatusService 
extends AbstractService<SocialStatus> 
{
    private static final long serialVersionUID = -7231855368627027760L;

    public SocialStatusService(SessionFactory factory) throws RemoteException
    {
        super(factory);
    }

    public boolean remove(Serializable id) throws RemoteException
    {
        connect();
        
        SocialStatus socialStatus = session.get(SocialStatus.class, id);
        
        if (socialStatus != null)
        {
            session.remove(socialStatus);
            session.getTransaction().commit();
            
            return true;
        }
        
        return false;
    }

    public SocialStatus find(Serializable id) throws RemoteException
    {
        connect();
        
        SocialStatus socialStatus = session.find(SocialStatus.class, id);
        session.getTransaction().commit();
        
        return socialStatus;
    }

    public List<SocialStatus> findAll() throws RemoteException
    {
        connect();
        
        TypedQuery<SocialStatus> query = session.createQuery("SELECT a FROM SocialStatus a", SocialStatus.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }    
}
