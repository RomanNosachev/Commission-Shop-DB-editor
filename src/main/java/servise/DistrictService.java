package servise;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

import dao.District;

public class DistrictService 
extends AbstractServise<District>
{
    public DistrictService(SessionFactory factory)
    {
        super(factory);
    }

    public District create(String name)
    {
        connect();
        
        District district = new District(name);   
        
        session.save(district);
        session.getTransaction().commit();
        
        return district;
    }
    
    public void create(District object)
    {
        connect();
        
        session.save(object);
        session.getTransaction().commit();
    }
    
    public boolean remove(Serializable id)
    {
        connect();
        
        District district = session.get(District.class, id);
        
        if (district != null)
        {
            session.remove(district);
            session.getTransaction().commit();
            
            return true;
        }
        
        return false;
    }

    public District find(Serializable id)
    {
        connect();
        
        District district = session.find(District.class, id);
        session.getTransaction().commit();
        
        return district;
    }

    public List<District> findAll()
    {
        connect();
        
        TypedQuery<District> query = session.createQuery("SELECT a FROM District a", District.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }
}
