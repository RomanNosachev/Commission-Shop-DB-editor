package servise;

import java.io.Serializable;
import java.util.List;

import dao.DB_Entity;

public interface Service<T extends DB_Entity>
{        
    public void             connect();
    public void             disconnect();
    
    public abstract void    create(T object);
    public abstract boolean remove(Serializable id);
    public abstract T       find(Serializable id);
    public abstract List<T> findAll();
    
    public Class<T> getEntityClass();
}
