package core;

import command.Command;
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
        
        if (!(msg instanceof Command))
            System.out.println("Unhandled command");
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
    {
        ctx.flush();
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        cause.printStackTrace();
        ctx.close();
    }
}