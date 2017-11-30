package core;

import inboundHandler.CreateCommandHandler;
import inboundHandler.FindAllCommandHandler;
import inboundHandler.FindCommandHandler;
import inboundHandler.LoginCommandHandler;
import inboundHandler.RemoveCommandHandler;
import inboundHandler.ServerDefaultHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public final class Server 
{    
    static final int     PORT = Integer.parseInt(System.getProperty("port", "8007"));
    
    public static void main(String[] args) throws Exception
    {                
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try
        {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception
                        {
                            ChannelPipeline p = ch.pipeline();

                            p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            p.addLast(new ObjectEncoder());
                            
                            p.addLast(new LoginCommandHandler());
                            p.addLast(new CreateCommandHandler());
                            p.addLast(new RemoveCommandHandler());
                            p.addLast(new FindCommandHandler());
                            p.addLast(new FindAllCommandHandler());
                            p.addLast(new ServerDefaultHandler());
                        }
                    });
            
            ChannelFuture f = b.bind(PORT).sync();            
            f.channel().closeFuture().sync();
        } 
        finally
        {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}