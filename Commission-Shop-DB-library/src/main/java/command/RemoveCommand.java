package command;

import java.io.Serializable;

import dao.DB_Entity;

public class RemoveCommand<T extends DB_Entity>
extends AbstractCommand<T>
{
    private static final long serialVersionUID = -3144729920858000465L;
    
    public Serializable id;
    
    public RemoveCommand(Class<T> entityClass)
    {
        super(entityClass);
    }
    
    public RemoveCommand(Serializable id)
    {
        this.id = id;
    }
}
