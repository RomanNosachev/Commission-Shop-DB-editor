package servise;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

import dao.Company;

public class CompanyService 
extends AbstractService<Company>
{            
    public CompanyService(SessionFactory factory)
    {
        super(factory);
    }
    
    public boolean remove(Serializable id)
    {
        connect();
        
        Company company = session.get(Company.class, id);
        
        if (company != null)
        {
            session.remove(session); 
            session.getTransaction().commit();
            
            return true;
        }
        
        return false;
    }

    public Company find(Serializable id)
    {
        connect();
        
        Company company = session.find(Company.class, id);
        session.getTransaction().commit();
        
        return company;
    }

    public List<Company> findAll()
    {
        connect();
        
        TypedQuery<Company> query = session.createQuery("SELECT a FROM Company a", Company.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }
}
