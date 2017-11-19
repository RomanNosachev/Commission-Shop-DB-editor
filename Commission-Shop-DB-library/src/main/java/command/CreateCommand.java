package command;

import dao.DB_Entity;

public class CreateCommand<T extends DB_Entity>
extends AbstractEntityCommand<T>
{
    private static final long serialVersionUID = -5879578281193027917L;
    
    private T entry;
    
    public CreateCommand(T entry)
    {
        if (entry == null)
            throw new NullPointerException();
        
        this.entry = entry;
        entityClass = (Class<T>) entry.getClass();
    }
    
    public T getEntry()
    {
        return entry;
    }
}
