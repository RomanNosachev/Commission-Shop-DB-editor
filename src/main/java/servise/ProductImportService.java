package servise;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

import dao.ProductImport;

public class ProductImportService 
extends AbstractService<ProductImport> 
{
    public ProductImportService(SessionFactory factory)
    {
        super(factory);
    }
    
    public boolean remove(Serializable id)
    {
        connect();
        
        ProductImport productImport = session.get(ProductImport.class, id);
        
        if (productImport != null)
        {
            session.remove(productImport);
            session.getTransaction().commit();
            
            return true;
        }
        
        return false;
    }
    
    public ProductImport find(Serializable id)
    {
        connect();
        
        ProductImport productImport = session.find(ProductImport.class, id);
        session.getTransaction().commit();
        
        return productImport;
    }
    
    public List<ProductImport> findAll()
    {
        connect();
        
        TypedQuery<ProductImport> query = session.createQuery("SELECT a FROM ProductImport a", ProductImport.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }
    
}
