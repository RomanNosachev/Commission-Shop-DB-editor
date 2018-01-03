package inboundHandler;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import command.LoginCommand;
import core.Server;
import dao.UserStatus;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import responce.LoginResponce;
import service.SessionFactoryUtils;

public class LoginCommandHandler 
extends SimpleChannelInboundHandler<LoginCommand>
{    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginCommand msg) throws Exception
    {
        try
        {
            SessionFactory factory = SessionFactoryUtils.getFactory(msg.getUser().getLogin(), msg.getUser().getPassword());
            
            Server.registerSessionFactory(ctx.channel().id(), factory);
            
            ctx.channel().writeAndFlush(new LoginResponce(true, UserStatus.User));
        }
        catch (HibernateException e)
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
