package servise;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

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
        connect();
        
        T entity = session.find(type, id);
        session.getTransaction().commit();
        
        return entity;
    }

    public List<T> findAll()
    {
        connect();
        
        Query<T> query = session.createQuery("SELECT a FROM " + type.getSimpleName() + " a", type);
        session.getTransaction().commit();
                        
        return query.getResultList();
    }
    
    public FindAllResponse<T> getFindAllResponse()
    {
        connect();
        
        Query<T> query = session.createQuery("SELECT a FROM " + type.getSimpleName() + " a", type);
        session.getTransaction().commit();
        
        return new FindAllResponse<T>(type, query.getResultList());
    }
}
