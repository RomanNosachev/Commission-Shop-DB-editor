package inboundHandler;

import controller.ClientController;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import response.LoginResponse;

public class LoginResponseHandler 
extends SimpleChannelInboundHandler<LoginResponse>
{    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponse msg) throws Exception
    {
        ClientController.Condition.setLogged(msg.isLogged());      
    }
}
