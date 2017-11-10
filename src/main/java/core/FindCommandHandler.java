package core;

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
        
        ctx.writeAndFlush(service.find(msg.getId()));
    } 
}
