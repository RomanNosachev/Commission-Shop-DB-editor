package command;

import java.io.Serializable;

import dao.DB_Entity;

public class RemoveCommand<T extends DB_Entity>
extends AbstractEntityCommand<T>
{
    private static final long serialVersionUID = -3144729920858000465L;
    
    private Serializable id;
    
    public RemoveCommand(Class<T> entityClass, Serializable id)
    {
        super(entityClass);
        
        if (id == null)
            throw new NullPointerException();
            
        this.id = id;
    }
    
    public Serializable getId()
    {
        return id;
    }
}
