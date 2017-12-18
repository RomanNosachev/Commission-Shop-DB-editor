package inboundHandler;

import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import response.FindAllResponse;

public class FindAllResponceHandler<T extends DB_Entity> 
extends AbstractResponseHandler<T>
{
    protected TableView<T> tableView;
    
    public FindAllResponceHandler(Class<T> entityClass, TableView<T> tableView)
    {
        super(entityClass);
        
        this.tableView = tableView;
    }

    @Override
    public void responceReceived(ChannelHandlerContext ctx, FindAllResponse<T> msg)
    {
        tableView.setItems(FXCollections.observableArrayList(msg.getEntries()));
    }
}
