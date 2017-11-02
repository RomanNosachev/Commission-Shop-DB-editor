package command;

import java.io.Serializable;

import dao.DB_Entity;

public interface Command<T extends DB_Entity>
extends Serializable
{
    public Class<T> getEntityClass();
}
