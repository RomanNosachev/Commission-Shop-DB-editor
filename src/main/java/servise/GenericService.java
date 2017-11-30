package servise;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import command.FetchMode;
import dao.DB_Entity;
import response.FindAllResponse;

public class GenericService<T extends DB_Entity, PK extends Serializable>
{
    private SessionFactory  factory;
    private Session         session;
    
    private Class<T>    type;
    
    public GenericService(Class<T> type)
    {
        this.type = type;
        factory = SessionFactoryUtils.factory;
    }
        
    public Class<T> getEntityClass()
    {
        return type;
    }
    
    public String getEntityName()
    {
        return type.getSimpleName();
    }
    
    public void connect()
    {
        if (session == null || !session.isOpen())
            session = factory.openSession();
        
        if (!session.getTransaction().isActive())
            session.beginTransaction();
    }
    
    public void disconnect()
    {
        if (session != null && session.isOpen())
        {
            session.close();
        }
    }
    
    public void create(T object) throws HibernateException
    {
        connect();
        
        session.save(object);
        session.getTransaction().commit();
    }
    
    public boolean remove(PK id)
    {
        connect();
        
        T entity = session.get(type, id);
        
        if (entity != null)
        {
            session.remove(entity);
            session.getTransaction().commit();
            
            return true;
        }
        
        return false;
    }

    public T find(PK id)
    {
        return find(id, FetchMode.EAGER);
    }
    
    public T find(PK id, FetchMode fetchMode)
    {
        connect();
        
        T entity;
                
        switch (fetchMode)
        {
            case EAGER:
                Map<String, Object> hints = new HashMap<>();
                hints.put("javax.persistence.fetchgraph", session.getEntityGraph(getEntityName()));
                entity = (T) session.find(getEntityClass(), id, hints);
                break;
                
            default:
                entity = session.find(type, id);
                break;
        }
        
        return entity;
    }

    public List<T> findAll()
    {
        return findAll(FetchMode.EAGER);
    }
    
    public List<T> findAll(FetchMode fetchMode)
    {
        connect();      
  
        TypedQuery<T> query = session.createQuery("FROM " + getEntityName() + " a", type);

        switch (fetchMode) 
        {
            case EAGER:
                query.setHint("javax.persistence.fetchgraph", session.getEntityGraph(getEntityName()));
                break;
            
            default:
                break;
        }
                
        List<T> resultList = query.getResultList();      
        return resultList;
    }
    
    public FindAllResponse<T> getFindAllResponse()
    {
        return getFindAllResponce(FetchMode.EAGER);
    }
    
    public FindAllResponse<T> getFindAllResponce(FetchMode fetchMode)
    {
        return new FindAllResponse<T>(type, findAll(fetchMode));
    }
}
