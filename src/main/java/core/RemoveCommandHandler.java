package core;

import command.RemoveCommand;
import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;

public class RemoveCommandHandler 
extends AbstractCommandHandler<RemoveCommand<? extends DB_Entity>>
{    
    @Override
    protected void commandReceived(ChannelHandlerContext ctx, RemoveCommand<? extends DB_Entity> msg) throws Exception
    {
        service.remove(msg.getId());
    }
}
