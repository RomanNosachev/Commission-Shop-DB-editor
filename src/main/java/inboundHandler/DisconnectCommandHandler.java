package inboundHandler;

import command.DisconnectCommand;
import core.Server;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DisconnectCommandHandler 
extends SimpleChannelInboundHandler<DisconnectCommand>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DisconnectCommand msg) throws Exception
    {
        Server.deregisterSessionFactory(ctx.channel().id());
        
        ctx.channel().closeFuture().sync();
    }   
}
