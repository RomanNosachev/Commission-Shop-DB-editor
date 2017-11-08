package core;
import command.CreateCommand;
import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;

public class CreateCommandHandler 
extends AbstractCommandHandler<CreateCommand<? extends DB_Entity>>
{        
    @Override
    public void commandReceived(ChannelHandlerContext ctx, CreateCommand<? extends DB_Entity> msg) throws Exception
    {
        service.create(msg.getEntry());    
    }  
}
