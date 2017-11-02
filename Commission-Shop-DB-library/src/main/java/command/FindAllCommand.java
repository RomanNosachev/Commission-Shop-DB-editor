package command;

import dao.DB_Entity;

public class FindAllCommand<T extends DB_Entity>
extends AbstractCommand<T>
{
    private static final long serialVersionUID = 153724217374908805L;

    public FindAllCommand(Class<T> entityClass)
    {
        super(entityClass);
    }
}
