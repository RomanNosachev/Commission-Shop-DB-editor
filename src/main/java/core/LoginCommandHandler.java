package core;

import command.LoginCommand;
import command.LoginResponceCommand;
import dao.User;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import util.DigestService;

public class LoginCommandHandler 
extends AbstractCommandHandler<LoginCommand>
{    
    @Override
    protected void commandReceived(ChannelHandlerContext ctx, LoginCommand msg) throws Exception
    {        
        if (!DigestService.isEqualsHash(msg.getUser(), (User) service.find(msg.getUser().getLogin())))
        {
            ChannelFuture f = ctx.channel().writeAndFlush(new LoginResponceCommand(false));
            f.addListener(new ChannelFutureListener() 
            {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception
                {
                    ctx.disconnect();
                }
            });
        }
        
        ctx.channel().writeAndFlush(new LoginResponceCommand(true));
    }
}
