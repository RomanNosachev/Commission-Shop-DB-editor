package inboundHandler;

import command.FindCommand;
import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;

public class FindCommandHandler 
extends AbstractCommandHandler<FindCommand<? extends DB_Entity>>
{    
    @Override
    protected void commandReceived(ChannelHandlerContext ctx, FindCommand<? extends DB_Entity> msg) throws Exception
    {
        System.out.println(msg.getId());
        
        DB_Entity entity = service.find(msg.getId());
        
        if (entity != null)
            ctx.writeAndFlush(entity);
        else
            // send failed find command
            return;
    } 
}
