package core;

import command.LoginCommand;
import io.netty.channel.ChannelHandlerContext;

public class LoginCommandHandler 
extends AbstractCommandHandler<LoginCommand>
{    
    @Override
    protected void commandReceived(ChannelHandlerContext ctx, LoginCommand msg) throws Exception
    {
        
    }
}
