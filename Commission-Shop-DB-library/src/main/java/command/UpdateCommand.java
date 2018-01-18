package command;

import dao.DB_Entity;

public class UpdateCommand<T extends DB_Entity>
extends AbstractEntityCommand<T>
{
    private static final long serialVersionUID = -5241518829756600702L;

    private T entry;
    
    @SuppressWarnings("unchecked")
    public UpdateCommand(T entry)
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
