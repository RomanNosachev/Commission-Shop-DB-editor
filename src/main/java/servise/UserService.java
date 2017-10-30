package servise;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

import dao.User;

public class UserService 
extends AbstractService<User>
{
    private static final long serialVersionUID = -457841376982877961L;

    public UserService(SessionFactory factory) throws RemoteException
    {
        super(factory);
    }

    public boolean remove(Serializable id) throws RemoteException
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

    public User find(Serializable id) throws RemoteException
    {
        connect();
        
        User user = session.find(User.class, id);
        session.getTransaction().commit();
        
        return user;
    }

    public List<User> findAll() throws RemoteException
    {
        connect();
        
        TypedQuery<User> query = session.createQuery("SELECT a FROM _User a", User.class);
        session.getTransaction().commit();
        
        return query.getResultList();
    }    
}
