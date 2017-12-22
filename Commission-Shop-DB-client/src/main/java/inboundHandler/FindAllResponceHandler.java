package inboundHandler;

import controller.EntityController;
import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;
import javafx.collections.FXCollections;
import response.FindAllResponse;

public class FindAllResponceHandler<T extends DB_Entity> 
extends AbstractResponseHandler<T>
{
    private EntityController<T> entityController;
    
    public FindAllResponceHandler(EntityController<T> entityController)
    {
        super(entityController.getEntityClass());       
        
        this.entityController = entityController;
    }

    @Override
    public void responceReceived(ChannelHandlerContext ctx, FindAllResponse<T> msg)
    {
        entityController.getTableView().setItems(FXCollections.observableArrayList(msg.getEntries()));
        entityController.setSheet(msg.getSheet());
    }
}
