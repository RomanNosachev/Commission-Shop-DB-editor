package core;

import dao.User;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class ServerHandler 
extends ChannelInboundHandlerAdapter 
{    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
    {
        ctx.write(msg);
        
        System.out.println(msg);
        
        if (msg instanceof User)
        {
            System.out.println(((User) msg).getLogin());
            System.out.println(((User) msg).getPassword());
        }        
        else
        {
            System.out.println("NO instance");
        }
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
    {
        ctx.flush();
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}