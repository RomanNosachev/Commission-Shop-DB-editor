package core;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class ClientHandler 
extends ChannelOutboundHandlerAdapter 
{           
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        // TODO Auto-generated method stub
        super.write(ctx, msg, promise);
    }
    
    /*
    @Override
    public void channelActive(ChannelHandlerContext ctx)
    {
        ctx.writeAndFlush(object);
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
    {
        //ctx.write(msg);
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
    */
}