package inboundHandler;

import command.RemoveCommand;
import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;

public class RemoveCommandHandler 
extends AbstractCommandHandler<RemoveCommand<? extends DB_Entity>>
{    
    @SuppressWarnings("unchecked")
    @Override
    protected void commandReceived(ChannelHandlerContext ctx, RemoveCommand<? extends DB_Entity> msg) throws Exception
    {
        try
        {
            service.remove(msg.getId());
        }
        catch (Exception e)
        {
            //TODO
            e.printStackTrace();
        }
    }
}
