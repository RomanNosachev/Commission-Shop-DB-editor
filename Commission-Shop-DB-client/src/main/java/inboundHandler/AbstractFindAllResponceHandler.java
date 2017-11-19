package inboundHandler;

import dao.DB_Entity;
import javafx.scene.control.TableView;

public abstract class AbstractFindAllResponceHandler<T extends DB_Entity> 
extends AbstractResponseHandler<T>
{
    protected TableView<T> tableView;
    
    public AbstractFindAllResponceHandler(Class<T> entityClass, TableView<T> tableView)
    {
        super(entityClass);
        
        this.tableView = tableView;
    }
}
