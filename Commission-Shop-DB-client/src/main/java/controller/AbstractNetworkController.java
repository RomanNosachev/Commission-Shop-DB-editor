package controller;

import command.Command;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import javafx.beans.property.BooleanProperty;

public abstract class AbstractNetworkController 
{    
    private String  host = System.getProperty("host", "127.0.0.1");
    private int     port = Integer.parseInt(System.getProperty("port", "8007"));
    
    protected Channel         channel;
    protected Bootstrap       bootstrap;
    
    private ChannelInitializer<SocketChannel> initializer;

    public AbstractNetworkController(ChannelInitializer<SocketChannel> initializer)
    {        
         this.initializer = initializer;
    }
    
    protected Bootstrap initBootstrap()
    {
        bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
            .channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true)
            .remoteAddress(host, port)
            .handler(initializer);   
        
        return bootstrap;
    }
    
    public abstract void acess(boolean value);
    public abstract void connect(String login, String password);
    public abstract void disconnect();
    public abstract void sendCommand(Command command);
    
    public abstract BooleanProperty getAcess();
}
