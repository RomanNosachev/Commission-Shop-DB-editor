package inboundHandler;

import controller.ClientController;
import dao.DB_Entity;
import io.netty.channel.ChannelHandlerContext;
import response.FindAllResponse;

public class FindAllResponseHandler 
extends AbstractResponseHandler<DB_Entity>
{    
    public FindAllResponseHandler(ClientController controller)
    {
        super(DB_Entity.class, controller);
    }

    @Override
    public void responceReceived(ChannelHandlerContext ctx, FindAllResponse<DB_Entity> msg)
    {
        //TODO
    }
}
