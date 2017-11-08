package servise;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import dao.Committent;

public class CommittentService 
extends AbstractService<Committent>
{
    public CommittentService()
    {
        super();
    }

    public boolean remove(Serializable id)
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

    public Committent find(Serializable id)
    {
        connect();
        
        Committent committent = session.find(Committent.class, id);
        session.getTransaction().commit();
        
        return committent;
    }

    public List<Committent> findAll()
    {
        connect();
        
        TypedQuery<Committent> query = session.createQuery("SELECT a FROM Committent a", Committent.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }

    @Override
    public Class<Committent> getEntityClass()
    {
        return Committent.class;
    }
}
