package core;

import command.LoginResponceCommand;
import controller.ClientController;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginResponceHandler 
extends SimpleChannelInboundHandler<LoginResponceCommand>
{    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponceCommand msg) throws Exception
    {
        ClientController.Condition.setLogged(msg.isLogged());      
    }
}
