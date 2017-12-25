package inboundHandler;

import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import responce.FindAllResponce;

public abstract class AbstractResponseHandler<T extends DB_Entity>
extends SimpleChannelInboundHandler<FindAllResponce<T>>
{
    private Class<T> entityClass;
        
    public AbstractResponseHandler(Class<T> entityClass)
    {
        this.entityClass = entityClass;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FindAllResponce<T> msg) throws Exception
    {
        if (msg.getEntityClass() != entityClass)
        {
            ctx.fireChannelRead(msg);
            
            return;
        }
        
        responceReceived(ctx, msg);
    }
    
    public abstract void responceReceived(ChannelHandlerContext ctx, FindAllResponce<T> msg);
}
