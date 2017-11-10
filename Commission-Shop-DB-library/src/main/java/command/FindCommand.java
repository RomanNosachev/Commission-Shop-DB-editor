package command;

import java.io.Serializable;

import dao.DB_Entity;

public class FindCommand<T extends DB_Entity>
extends AbstractEntityCommand<T>
{
    private static final long serialVersionUID = 3242647986042328383L;
    
    private Serializable id;
    
    public FindCommand(Class<T> entityClass)
    {
        super(entityClass);
    }
    
    public FindCommand(Serializable id)
    {
        this.id = id;
    }
    
    public Serializable getId()
    {
        return id;
    }
}
