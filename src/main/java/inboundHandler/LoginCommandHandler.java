package inboundHandler;

import command.LoginCommand;
import dao.UserStatus;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import responce.LoginResponce;

public class LoginCommandHandler 
extends AbstractCommandHandler<LoginCommand>
{    
    @SuppressWarnings("unchecked")
    @Override
    protected void commandReceived(ChannelHandlerContext ctx, LoginCommand msg) throws Exception
    {     
        try
        {
            UserStatus status = service.login(msg.getUser().getLogin(), msg.getUser().getPassword());
            
            ctx.channel().writeAndFlush(new LoginResponce(true, status));
        }
        catch (IllegalAccessException e)
        {
            ChannelFuture f = ctx.channel().writeAndFlush(new LoginResponce(false));
            f.addListener(new ChannelFutureListener() 
            {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception
                {
                    ctx.disconnect();
                }
            });
        }
    }
}
