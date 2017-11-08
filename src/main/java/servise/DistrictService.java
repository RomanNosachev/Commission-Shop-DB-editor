package servise;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import dao.District;

public class DistrictService 
extends AbstractService<District>
{
    public DistrictService()
    {
        super();
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

    @Override
    public Class<District> getEntityClass()
    {
        return District.class;
    }
}
