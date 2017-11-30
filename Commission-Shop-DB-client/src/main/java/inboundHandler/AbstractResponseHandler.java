package inboundHandler;

import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import response.FindAllResponse;

public abstract class AbstractResponseHandler<T extends DB_Entity>
extends SimpleChannelInboundHandler<FindAllResponse<T>>
{
    private Class<T> entityClass;
        
    public AbstractResponseHandler(Class<T> entityClass)
    {
        this.entityClass = entityClass;
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
