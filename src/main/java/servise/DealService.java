package servise;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import dao.Deal;

public class DealService 
extends AbstractService<Deal> 
{
    public DealService()
    {
        super();
    }

    public boolean remove(Serializable id)
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

    public Deal find(Serializable id)
    {
        connect();
        
        Deal deal = session.find(Deal.class, id);
        session.getTransaction().commit();
        
        return deal;
    }

    public List<Deal> findAll()
    {
        connect();
        
        TypedQuery<Deal> query = session.createQuery("SELECT a FROM Deal a", Deal.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }

    @Override
    public Class<Deal> getEntityClass()
    {
        return Deal.class;
    }   
}
