package command;

import dao.DB_Entity;

public abstract class AbstractCommand<T extends DB_Entity>
implements Command<T>
{    
    private static final long serialVersionUID = 3349589021588325370L;
    
    Class<T> entityClass;
    
    public AbstractCommand()
    {
        
    }
    
    protected AbstractCommand(Class<T> entityClass)
    {
        this.entityClass = entityClass;
    }
    
    public Class<T> getEntityClass()
    {
        return entityClass;
    }
}
