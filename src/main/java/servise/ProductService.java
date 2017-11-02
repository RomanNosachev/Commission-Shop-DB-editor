package servise;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import dao.Product;

public class ProductService 
extends AbstractService<Product> 
{
    public ProductService()
    {
        super();
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
