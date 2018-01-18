package dao;

import java.io.Serializable;

public interface DB_Entity
extends Serializable
{
    public Serializable getPK();
    
    public void merge(DB_Entity object);
}
