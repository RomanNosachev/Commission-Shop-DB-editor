package command;

import dao.DB_Entity;

public class FindAllCommand<T extends DB_Entity>
extends AbstractEntityCommand<T>
{
    private static final long serialVersionUID = 153724217374908805L;
    
    private FetchMode fetchMode = FetchMode.EAGER;
    private int sheet = 1;

    public FindAllCommand(Class<T> entityClass)
    {
        super(entityClass);        
    }
    
    public FindAllCommand(Class<T> entityClass, FetchMode fetchMode, int sheet)
    {
        this(entityClass);
        
        this.fetchMode = fetchMode;
        this.sheet = sheet;
    }
    
    public FetchMode getFetchMode()
    {
        return fetchMode;
    }
    
    public int getSheet()
    {
        return sheet;
    }
}
