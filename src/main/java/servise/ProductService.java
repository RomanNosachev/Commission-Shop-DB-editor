package servise;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

import dao.Product;

public class ProductService 
extends AbstractServise<Product> 
{
    public ProductService(SessionFactory factory)
    {
        super(factory);
    }

    public void create(Product object)
    {
        connect();
        
        session.save(object);
        session.getTransaction().commit();
    }

    public boolean remove(Serializable id)
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

    public Product find(Serializable id)
    {
        connect();
        
        Product product = session.find(Product.class, id);
        session.getTransaction().commit();
        
        return product;
    }

    public List<Product> findAll()
    {
        connect();
        
        TypedQuery<Product> query = session.createQuery("SELECT a FROM Product a", Product.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }
}
