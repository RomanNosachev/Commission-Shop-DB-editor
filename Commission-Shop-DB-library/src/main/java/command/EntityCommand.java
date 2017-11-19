package command;

import dao.DB_Entity;

public interface EntityCommand<T extends DB_Entity>
extends Command
{
    public Class<T> getEntityClass();
}
