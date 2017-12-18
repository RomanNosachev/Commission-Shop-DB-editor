package service;

import dao.DB_Entity;

public interface EntityGenerator<T extends DB_Entity>
{
    public T generate();
}
