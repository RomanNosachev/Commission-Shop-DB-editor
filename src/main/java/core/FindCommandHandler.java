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
        service.find(msg.getId());
    } 
}
