package controller;

import command.Command;
import command.CreateCommand;
import core.ClientHandler;
import dao.Company;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ClientController 
{    
    private String  host = System.getProperty("host", "127.0.0.1");
    private int     port = Integer.parseInt(System.getProperty("port", "8007"));
    
    private BooleanProperty connected = new SimpleBooleanProperty();
    private BooleanProperty logged = new SimpleBooleanProperty();
    
    private Channel         channel;
    private EventLoopGroup  group;
    
    @FXML
    private TextArea        area;
    @FXML
    private Pane            connectPane;
    @FXML 
    private Pane            disconnectPane;
    @FXML
    private TextField       loginField;
    @FXML
    private PasswordField   passField;
        
    @FXML
    public void initialize()
    {
        connectPane.disableProperty().bind(logged);
        connectPane.visibleProperty().bind(logged.not());
        disconnectPane.disableProperty().bind(logged.not());
        disconnectPane.visibleProperty().bind(logged);
        
        /*
        connectPane.disableProperty().bind(connected);
        connectPane.visibleProperty().bind(connected.not());
        disconnectPane.disableProperty().bind(connected.not());
        disconnectPane.visibleProperty().bind(connected);
        */
    }
    
    @FXML
    public void connect()
    {        
        group = new NioEventLoopGroup();
        
        Task<Channel> task = new Task<Channel>() 
        {
            @Override
            protected Channel call() throws Exception
            {
                Bootstrap b = new Bootstrap();
                b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .remoteAddress(host, port)
                    .handler(new ChannelInitializer<SocketChannel>() 
                        {
                            @Override
                            public void initChannel(SocketChannel ch) throws Exception
                            {
                                ChannelPipeline p = ch.pipeline();
                                
                                p.addLast(new ObjectEncoder());
                                p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                                p.addLast(new ClientHandler());
                            }
                        });
                
                ChannelFuture f = b.connect();
                Channel chn = f.channel();
                
                f.sync();
                
                return chn;
            }
            
            @Override
            protected void succeeded() 
            {
                channel = getValue();
                connected.set(true);
            }

            @Override
            protected void failed() 
            {
                Throwable exc = getException();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Client");
                alert.setHeaderText(exc.getClass().getName());
                alert.setContentText(exc.getMessage());
                alert.showAndWait();
                
                connected.set(false);
            }
        };
        
        new Thread(task).start();
    }
    
    @FXML
    public void login()
    {
        if (!connected.get())
            return;
        
        Task<Boolean> task = new Task<Boolean>() 
        {
            @Override
            protected Boolean call() throws Exception
            {
                ChannelFuture f = channel.writeAndFlush(null);
                f.sync();
                
                return false;
            }
            
            @Override
            protected void succeeded() 
            {
                logged.set(true);
            }

            @Override
            protected void failed() 
            {
                Throwable exc = getException();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Client");
                alert.setHeaderText(exc.getClass().getName());
                alert.setContentText(exc.getMessage());
                alert.showAndWait();
                
                logged.set(false);
            }
        };
        
        new Thread(task).start();
    }
    
    @FXML
    public void disconnect()
    {
        if (!connected.get())
            return;
        
        connected.set(false);
        logged.set(false);
        
        Task<Void> task = new Task<Void>() 
        {
            @Override
            protected Void call() throws Exception
            {
                channel.close().sync();
                group.shutdownGracefully().sync();
                                
                return null;
            }

            @Override
            protected void failed() 
            {
                Throwable t = getException();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Client");
                alert.setHeaderText(t.getClass().getName());
                alert.setContentText(t.getMessage());
                alert.showAndWait();
            }
        };
        
        new Thread(task).start();
    }
    
    @FXML
    public void test()
    {
        if (!connected.get() || !logged.get())
            return;
        
        Company company = new Company("Blizzard");
        final Command<Company> command = new CreateCommand<Company>(company);
        
        Task<Void> task = new Task<Void>() 
        {
            @Override
            protected Void call() throws Exception
            {
                ChannelFuture f = channel.writeAndFlush(command);
                f.sync();
                
                return null;
            }
            
            @Override
            protected void failed() 
            {
                Throwable exc = getException();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Client");
                alert.setHeaderText(exc.getClass().getName());
                alert.setContentText(exc.getMessage());
                alert.showAndWait();
                
                connected.set(false);
            }
        };
        
        new Thread(task).start();
    }
}
