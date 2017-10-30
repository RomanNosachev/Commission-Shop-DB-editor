package servise;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

import dao.District;

public class DistrictService 
extends AbstractService<District>
{
    private static final long serialVersionUID = -1773231323548014970L;

    public DistrictService(SessionFactory factory) throws RemoteException
    {
        super(factory);
    }

    public boolean remove(Serializable id) throws RemoteException
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

    public District find(Serializable id) throws RemoteException
    {
        connect();
        
        District district = session.find(District.class, id);
        session.getTransaction().commit();
        
        return district;
    }

    public List<District> findAll() throws RemoteException
    {
        connect();
        
        TypedQuery<District> query = session.createQuery("SELECT a FROM District a", District.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }
}
