package command;

import dao.DB_Entity;

public class CreateCommand<T extends DB_Entity>
extends AbstractCommand<T>
{
    private static final long serialVersionUID = -5879578281193027917L;
    
    private T entry;
    
    public CreateCommand(Class<T> entityClass)
    {
        super(entityClass);
    }
    
    public CreateCommand(T entry)
    {
        this.entry = entry;
        entityClass = (Class<T>) entry.getClass();
    }
    
    public T getEntry()
    {
        return entry;
    }
}
