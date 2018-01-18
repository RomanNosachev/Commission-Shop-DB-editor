package inboundHandler;

import java.io.Serializable;

import org.hibernate.SessionFactory;

import command.EntityCommand;
import core.Server;
import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
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
        serviceInstance(ctx.channel().id(), msg.getEntityClass());
        commandReceived(ctx, msg);
    }
    
    @SuppressWarnings("unchecked")
    private GenericService<?, Serializable> serviceInstance(ChannelId id, Class<? extends DB_Entity> entityClass)
    {
        SessionFactory factory = Server.getSessionFactory(id);
        
        if (factory == null)
            throw new NullPointerException();
        
        if (service == null || service.getEntityClass() != entityClass)
            service = new GenericService<>(entityClass, factory);
       
        return service;        
    }
    
    protected abstract void commandReceived(ChannelHandlerContext ctx, T msg) throws Exception;
}
