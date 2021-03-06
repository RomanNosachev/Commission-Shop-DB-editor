package service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import command.FetchMode;
import dao.DB_Entity;
import response.FindAllResponse;

public class GenericService<T extends DB_Entity, PK extends Serializable>
{
    public final int   SHEET_SIZE = 100;
    
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
        
        disconnect();
    }
    
    //TODO
    public void update(T object)
    {
        connect();
        
        session.update(object);
        session.getTransaction().commit();
        
        disconnect();
    }
    
    public boolean remove(PK id)
    {
        connect();
        
        T entity = session.get(type, id);
        
        boolean removed = false;
        
        if (entity != null)
        {
            session.remove(entity);
            session.getTransaction().commit();
            
            removed = true;
        }
        
        disconnect();
        
        return removed;
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
        
        disconnect();
        
        return entity;
    }

    public List<T> findAll()
    {
        return findAll(FetchMode.EAGER, 1);
    }
    
    public List<T> findAll(FetchMode fetchMode, int startPosition)
    {
        connect();      

        TypedQuery<T> query = session.createQuery("FROM " + getEntityName() + " a", type)   
                .setFirstResult(startPosition)
                .setMaxResults(SHEET_SIZE);
        
        switch (fetchMode) 
        {
            case EAGER:
                query.setHint("javax.persistence.fetchgraph", session.getEntityGraph(getEntityName()));
                break;
            
            default:
                break;
        }
                
        List<T> resultList = query.getResultList();     
        
        disconnect();
        
        return resultList;
    }
    
    public FindAllResponse<T> getFindAllResponse()
    {
        return getFindAllResponce(FetchMode.EAGER, 1);
    }
    
    public FindAllResponse<T> getFindAllResponce(FetchMode fetchMode, int sheet)
    {
        long count = count();
        int currentSheet = (int) Math.min(sheet, (count - 1) / SHEET_SIZE + 1);
        int startPosition = (int) Math.min((currentSheet - 1) * SHEET_SIZE + 1, count);
        
        return new FindAllResponse<T>(type, findAll(fetchMode, startPosition), currentSheet);
    }
    
    public long count()
    {
        connect();      
        
        Query countQuery = session.createQuery("SELECT COUNT(*) FROM " + getEntityName() + " a");
        long count = (long) countQuery.getSingleResult();
        
        disconnect();
        
        return count;
    } 
}
