package command;

import dao.DB_Entity;

public class UpdateCommand<T extends DB_Entity>
extends CreateCommand<T>
{
    private static final long serialVersionUID = -5241518829756600702L;

    public UpdateCommand(T entry)
    {
        super(entry);
    }
}
