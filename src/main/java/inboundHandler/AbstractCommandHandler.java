package inboundHandler;

import java.io.Serializable;

import command.EntityCommand;
import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import service.GenericService;

public abstract class AbstractCommandHandler<T extends EntityCommand<? extends DB_Entity>> 
extends SimpleChannelInboundHandler<T>
{
    @SuppressWarnings("rawtypes")
    GenericService service;
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception
    {
        serviceInstance(msg);
        commandReceived(ctx, msg);
    }
    
    private GenericService<?, Serializable> serviceInstance(EntityCommand<? extends DB_Entity> msg)
    {
        /*
        if (service == null)
            service = new GenericService<>(msg.getEntityClass());
        else
            if (service.getEntityClass() != msg.getEntityClass())
            {
                try
                {
                    User user = service.getUser();
                    service = new GenericService<>(msg.getEntityClass());
                    service.login(user.getLogin(), user.getPassword());
                }
                catch(IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
                      
        return service;
        */
        
        return new GenericService<>(msg.getEntityClass());
    }
    
    protected abstract void commandReceived(ChannelHandlerContext ctx, T msg) throws Exception;
}
