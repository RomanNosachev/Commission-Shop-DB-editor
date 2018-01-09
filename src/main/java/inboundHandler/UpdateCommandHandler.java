package inboundHandler;

import command.UpdateCommand;
import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;

public class UpdateCommandHandler 
extends AbstractCommandHandler<UpdateCommand<? extends DB_Entity>>
{
    @SuppressWarnings("unchecked")
    @Override
    protected void commandReceived(ChannelHandlerContext ctx, UpdateCommand<? extends DB_Entity> msg) throws Exception
    {
        try
        {
            service.update(msg.getEntry());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    } 
}
