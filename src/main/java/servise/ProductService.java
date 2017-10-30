package servise;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

import dao.Product;

public class ProductService 
extends AbstractService<Product> 
{
    private static final long serialVersionUID = -5639337080561109032L;

    public ProductService(SessionFactory factory) throws RemoteException
    {
        super(factory);
    }

    public boolean remove(Serializable id) throws RemoteException
    {
        connect();
        
        Product product = session.get(Product.class, id);
        
        if (product != null)
        {
            session.remove(product);
            session.getTransaction().commit();
            
            return true;
        }
       
        return false; 
    }

    public Product find(Serializable id) throws RemoteException
    {
        connect();
        
        Product product = session.find(Product.class, id);
        session.getTransaction().commit();
        
        return product;
    }

    public List<Product> findAll() throws RemoteException
    {
        connect();
        
        TypedQuery<Product> query = session.createQuery("SELECT a FROM Product a", Product.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }
}
