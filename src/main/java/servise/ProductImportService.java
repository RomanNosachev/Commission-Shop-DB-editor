package servise;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

import dao.ProductImport;

public class ProductImportService 
extends AbstractService<ProductImport> 
{
    private static final long serialVersionUID = 6159938425066651392L;

    public ProductImportService(SessionFactory factory) throws RemoteException
    {
        super(factory);
    }
    
    public boolean remove(Serializable id) throws RemoteException
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
    
    public ProductImport find(Serializable id) throws RemoteException
    {
        connect();
        
        ProductImport productImport = session.find(ProductImport.class, id);
        session.getTransaction().commit();
        
        return productImport;
    }
    
    public List<ProductImport> findAll() throws RemoteException
    {
        connect();
        
        TypedQuery<ProductImport> query = session.createQuery("SELECT a FROM ProductImport a", ProductImport.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }
    
}
