package inboundHandler;

import controller.ClientController;
import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import response.FindAllResponse;

public abstract class AbstractResponseHandler<T extends DB_Entity>
extends SimpleChannelInboundHandler<FindAllResponse<T>>
{
    private Class<T> entityClass;
    
    protected ClientController controller;
    
    public AbstractResponseHandler(Class<T> entityClass, ClientController controller)
    {
        this.entityClass = entityClass;
        this.controller = controller;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FindAllResponse<T> msg) throws Exception
    {
        if (msg.getEntityClass() != entityClass)
        {
            ctx.fireChannelRead(msg);
            
            return;
        }
        
        responceReceived(ctx, msg);
    }
    
    public abstract void responceReceived(ChannelHandlerContext ctx, FindAllResponse<T> msg);
}
