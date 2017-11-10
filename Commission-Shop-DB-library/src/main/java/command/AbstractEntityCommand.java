package command;

import dao.DB_Entity;

public abstract class AbstractEntityCommand<T extends DB_Entity>
implements EntityCommand<T>
{    
    private static final long serialVersionUID = 3349589021588325370L;
    
    Class<T> entityClass;
    
    public AbstractEntityCommand()
    {
        
    }
    
    protected AbstractEntityCommand(Class<T> entityClass)
    {
        this.entityClass = entityClass;
    }
    
    public Class<T> getEntityClass()
    {
        return entityClass;
    }
}
