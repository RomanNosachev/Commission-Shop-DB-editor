package inboundHandler;

import command.FindAllCommand;
import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;

public class FindAllCommandHandler 
extends AbstractCommandHandler<FindAllCommand<? extends DB_Entity>>
{    
    @Override
    protected void commandReceived(ChannelHandlerContext ctx, FindAllCommand<? extends DB_Entity> msg) throws Exception
    {
        ctx.writeAndFlush(service.getFindAllResponce(msg.getFetchMode(), msg.getSheet()));
    }
}
