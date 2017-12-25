package inboundHandler;

import controller.EntityController;
import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FindResponceHandler<T extends DB_Entity>
extends SimpleChannelInboundHandler<T>
{
    private EntityController<T> entityController;
    
    public FindResponceHandler(EntityController<T> entityController)
    {
        super(entityController.getEntityClass());
        
        this.entityController = entityController;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception
    {
        ObservableList<T> items = FXCollections.observableArrayList();
        items.add(msg);
        
        entityController.getTableView().setItems(items);
    }
}
