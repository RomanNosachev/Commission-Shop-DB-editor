package inboundHandler;

import controller.ClientController;
import dao.District;
import io.netty.channel.ChannelHandlerContext;
import response.FindAllResponse;

public class FindAllDistrictResponseHandler 
extends AbstractResponseHandler<District>
{
    public FindAllDistrictResponseHandler(ClientController controller)
    {
        super(District.class, controller);
    }

    @Override
    public void responceReceived(ChannelHandlerContext ctx, FindAllResponse<District> msg)
    {
        //TODO
    }
}
