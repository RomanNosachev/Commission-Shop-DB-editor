package core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.SessionFactory;

import inboundHandler.CreateCommandHandler;
import inboundHandler.DisconnectCommandHandler;
import inboundHandler.FindAllCommandHandler;
import inboundHandler.FindCommandHandler;
import inboundHandler.LoginCommandHandler;
import inboundHandler.RemoveCommandHandler;
import inboundHandler.ServerDefaultHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelId;
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
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    
    private static Map<ChannelId, SessionFactory> factories;
    
    public static void registerSessionFactory(ChannelId id, SessionFactory factory) 
    {
        factories.put(id, factory);       
    }
    
    public static void deregisterSessionFactory(ChannelId id)
    {
        factories.remove(id);
    }
    
    public static SessionFactory getSessionFactory(ChannelId id)
    {
        return factories.get(id);
    }
    
    public static void main(String[] args) throws Exception
    {                
        factories = new ConcurrentHashMap<>();
        
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
                            
                            p.addFirst(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            p.addFirst(new ObjectEncoder());
                            
                            p.addLast(new LoginCommandHandler());
                            p.addLast(new DisconnectCommandHandler());
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