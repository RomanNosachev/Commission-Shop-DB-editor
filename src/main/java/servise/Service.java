package servise;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Service<T>
extends Remote
{        
    public void             connect() throws RemoteException;
    public void             disconnect() throws RemoteException;
    
    public abstract void    create(T object) throws RemoteException;
    public abstract boolean remove(Serializable id) throws RemoteException;
    public abstract T       find(Serializable id) throws RemoteException;
    public abstract List<T> findAll() throws RemoteException;
}
