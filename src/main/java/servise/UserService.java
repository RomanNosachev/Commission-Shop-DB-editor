package servise;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import dao.User;

public class UserService 
extends AbstractService<User>
{
    public UserService()
    {
        super();
    }

    public boolean remove(Serializable id)
    {
        connect();
        
        User user = session.get(User.class, id);
        
        if (user != null)
        {
            session.remove(user);
            session.getTransaction().commit();
            
            return true;
        }
        
        return false;
    }

    public User find(Serializable id)
    {
        connect();
        
        User user = session.find(User.class, id);
        session.getTransaction().commit();
        
        return user;
    }

    public List<User> findAll()
    {
        connect();
        
        TypedQuery<User> query = session.createQuery("SELECT a FROM _User a", User.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }    
}
