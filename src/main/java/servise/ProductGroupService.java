package servise;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

import dao.ProductGroup;

public class ProductGroupService 
extends AbstractService<ProductGroup> 
{
    private static final long serialVersionUID = 6303615125896442532L;

    public ProductGroupService(SessionFactory factory) throws RemoteException
    {
        super(factory);
    }

    public boolean remove(Serializable id) throws RemoteException
    {
        connect();

        ProductGroup productGroup = session.get(ProductGroup.class, id);
        
        if (productGroup != null)
        {
            session.remove(productGroup);
            session.getTransaction().commit();
            
            return true;
        }
        
        return false;
    }

    public ProductGroup find(Serializable id) throws RemoteException
    {
        connect();
        
        ProductGroup productGroup = session.find(ProductGroup.class, id);
        session.getTransaction().commit();
        
        return productGroup;
    }

    public List<ProductGroup> findAll() throws RemoteException
    {
        connect();
        
        TypedQuery<ProductGroup> query = session.createQuery("SELECT a FROM ProductGroup a", ProductGroup.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }
}
