package core;

import command.Command;
import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import servise.GenericService;

public abstract class AbstractCommandHandler<T extends Command<? extends DB_Entity>> 
extends SimpleChannelInboundHandler<T>
{
    GenericService service;
        
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception
    {
        serviceInstance(msg);
        commandReceived(ctx, msg);
    }
    
    private GenericService serviceInstance(Command<? extends DB_Entity> msg)
    {
        if (service == null || service.getEntityClass() != msg.getEntityClass())
            service = new GenericService<>(msg.getEntityClass());
        
        return service;
    }
    
    protected abstract void commandReceived(ChannelHandlerContext ctx, T msg) throws Exception;
}
