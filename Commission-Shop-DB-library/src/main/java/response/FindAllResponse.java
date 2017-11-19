package response;

import java.util.ArrayList;
import java.util.List;

import command.AbstractEntityCommand;
import dao.DB_Entity;

public class FindAllResponse<T extends DB_Entity> 
extends AbstractEntityCommand<T>
{    
    private static final long serialVersionUID = -3128269021277329296L;

    private ArrayList<T> entries;
    
    public FindAllResponse(Class<T> entityClass, List<T> entries)
    {
        super(entityClass);
        
        this.entries = new ArrayList<T>(entries);
    }

    public List<T> getEntries()
    {
        return entries;
    }  
}
