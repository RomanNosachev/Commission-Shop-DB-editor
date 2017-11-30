package command;

import java.io.Serializable;

import dao.DB_Entity;

public class FindCommand<T extends DB_Entity>
extends AbstractEntityCommand<T>
{
    private static final long serialVersionUID = 3242647986042328383L;
    
    private Serializable id;
    private FetchMode fetchMode = FetchMode.EAGER;
    
    public FindCommand(Class<T> entityClass, Serializable id)
    {
        super(entityClass);
        
        if (id == null)
            throw new NullPointerException();
            
        this.id = id;
    }

    public FindCommand(Class<T> entityClass, Serializable id, FetchMode fetchMode)
    {
        this(entityClass, id);
        
        this.fetchMode = fetchMode;
    }
    
    public Serializable getId()
    {
        return id;
    }
    
    public FetchMode getFetchMode()
    {
        return fetchMode;
    }
}
