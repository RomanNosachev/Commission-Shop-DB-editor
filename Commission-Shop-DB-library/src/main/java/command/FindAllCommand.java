package command;

import dao.DB_Entity;

public class FindAllCommand<T extends DB_Entity>
extends AbstractEntityCommand<T>
{
    private static final long serialVersionUID = 153724217374908805L;
    
    private FetchMode fetchMode = FetchMode.EAGER;

    public FindAllCommand(Class<T> entityClass)
    {
        super(entityClass);        
    }
    
    public FindAllCommand(Class<T> entityClass, FetchMode fetchMode)
    {
        this(entityClass);
        
        this.fetchMode = fetchMode;
    }
    
    public FetchMode getFetchMode()
    {
        return fetchMode;
    }
}
