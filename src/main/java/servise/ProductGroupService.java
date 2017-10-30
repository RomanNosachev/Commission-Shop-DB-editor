package servise;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

import dao.ProductGroup;

public class ProductGroupService 
extends AbstractService<ProductGroup> 
{
    public ProductGroupService(SessionFactory factory)
    {
        super(factory);
    }

    public boolean remove(Serializable id)
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

    public ProductGroup find(Serializable id)
    {
        connect();
        
        ProductGroup productGroup = session.find(ProductGroup.class, id);
        session.getTransaction().commit();
        
        return productGroup;
    }

    public List<ProductGroup> findAll()
    {
        connect();
        
        TypedQuery<ProductGroup> query = session.createQuery("SELECT a FROM ProductGroup a", ProductGroup.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }
}
