package inboundHandler;

import command.FindCommand;
import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;

public class FindCommandHandler 
extends AbstractCommandHandler<FindCommand<? extends DB_Entity>>
{    
    @SuppressWarnings("unchecked")
    @Override
    protected void commandReceived(ChannelHandlerContext ctx, FindCommand<? extends DB_Entity> msg) throws Exception
    {        
        DB_Entity entity = service.find(msg.getId(), msg.getFetchMode());
        
        if (entity != null)
            ctx.writeAndFlush(entity);
    } 
}
