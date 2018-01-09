package inboundHandler;
import command.CreateCommand;
import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;

public class CreateCommandHandler 
extends AbstractCommandHandler<CreateCommand<? extends DB_Entity>>
{        
    @SuppressWarnings("unchecked")
    @Override
    public void commandReceived(ChannelHandlerContext ctx, CreateCommand<? extends DB_Entity> msg) throws Exception
    {
        try
        {
            service.create(msg.getEntry());
        }
        catch (Exception e) 
        {
            //TODO
            System.err.println(e.getMessage());
        }
    }  
}
