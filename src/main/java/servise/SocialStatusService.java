package servise;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

import dao.SocialStatus;

public class SocialStatusService 
extends AbstractService<SocialStatus> 
{
    public SocialStatusService(SessionFactory factory)
    {
        super(factory);
    }

    public boolean remove(Serializable id)
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

    public SocialStatus find(Serializable id)
    {
        connect();
        
        SocialStatus socialStatus = session.find(SocialStatus.class, id);
        session.getTransaction().commit();
        
        return socialStatus;
    }

    public List<SocialStatus> findAll()
    {
        connect();
        
        TypedQuery<SocialStatus> query = session.createQuery("SELECT a FROM SocialStatus a", SocialStatus.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }    
}
