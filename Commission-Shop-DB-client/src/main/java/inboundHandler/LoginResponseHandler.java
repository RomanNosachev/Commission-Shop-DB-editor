package inboundHandler;

import controller.AbstractNetworkController;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import responce.LoginResponce;

public class LoginResponseHandler 
extends SimpleChannelInboundHandler<LoginResponce>
{    
    private AbstractNetworkController controller;
    
    public LoginResponseHandler(AbstractNetworkController controller)
    {
        this.controller = controller;
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponce msg) throws Exception
    {
        controller.setAccess(msg.isLogged());
        controller.setUserStatus(msg.getStatus().ordinal());
    }
}
