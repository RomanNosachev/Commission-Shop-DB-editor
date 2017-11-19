package inboundHandler;

import controller.ClientController;
import dao.Company;
import io.netty.channel.ChannelHandlerContext;
import response.FindAllResponse;

public class FindAllCompanyResponseHandler 
extends AbstractResponseHandler<Company>
{
    public FindAllCompanyResponseHandler(ClientController controller)
    {        
        super(Company.class, controller);
    }

    @Override
    public void responceReceived(ChannelHandlerContext ctx, FindAllResponse<Company> msg)
    {
        //TODO
    }
}
