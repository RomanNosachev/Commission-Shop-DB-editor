package servise;

import java.io.Serializable;
import java.util.List;

public interface Service<T>
{        
    public void             connect();
    public void             disconnect();
    
    public abstract void    create(T object);
    public abstract boolean remove(Serializable id);
    public abstract T       find(Serializable id);
    public abstract List<T> findAll();
}
