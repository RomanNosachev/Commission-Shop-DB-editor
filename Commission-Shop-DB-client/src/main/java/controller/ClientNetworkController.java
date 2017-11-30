package controller;

import command.Command;
import command.EntityCommand;
import command.LoginCommand;
import dao.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClientNetworkController 
extends AbstractNetworkController
{
    private String  login = "";
    private String  password = "";
    
    private static class Condition
    {
        private volatile static BooleanProperty connected = new SimpleBooleanProperty(false);
        private volatile static BooleanProperty logged = new SimpleBooleanProperty(false);

        private static synchronized void setConnected(boolean value)
        {
            connected.set(value);
        }
        
        private static synchronized void setLogged(boolean value)
        {
            logged.set(value);
        }
        
        public static synchronized boolean isConnected()
        {
            return connected.get();
        }
        
        public static synchronized boolean isReady()
        {
            return connected.and(logged).get();
        }

        public static BooleanProperty getLogged()
        {
            return logged;
        }
    }

    public ClientNetworkController(final ChannelInitializer<SocketChannel> channelInitializer)
    {
        super(channelInitializer);
    }
    
    @Override
    public void connect(String login, String password)
    {                
        Task<Channel> task = new Task<Channel>() 
        {
            @Override
            protected Channel call() throws Exception
            {  
                initBootstrap();
                
                ChannelFuture future = bootstrap.connect();
                Channel channel = future.channel();
                
                future.sync();
                
                return channel;
            }
            
            @Override
            protected void succeeded() 
            {
                channel = getValue();
                Condition.setConnected(true);
                
                login(login, password);
            }

            @Override
            protected void failed() 
            {
                Throwable exception = getException();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Client");
                alert.setHeaderText(exception.getClass().getName());
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
                
                Condition.setConnected(false);
            }
        };
        
        new Thread(task).start();
    }
    
    public void reconnect()
    {
        Task<Channel> task = new Task<Channel>() 
        {
            @Override
            protected Channel call() throws Exception
            {
                initBootstrap();
                
                ChannelFuture future = bootstrap.connect();
                Channel channel = future.channel();
                
                future.sync();
                
                return channel;
            }
            
            @Override
            protected void succeeded() 
            {
                channel = getValue();
                Condition.setConnected(true);
                
                login(login, password);
            }

            @Override
            protected void failed() 
            {
                Throwable exception = getException();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Client");
                alert.setHeaderText(exception.getClass().getName());
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
                
                Condition.setConnected(false);
            }
        };
        
        new Thread(task).start();
    }
    
    public void login(String login, String password)
    {
        if (!Condition.isConnected())
            return;
        
        Task<Void> task = new Task<Void>() 
        {
            @Override
            protected Void call() throws Exception
            {
                User user = new User(login, password);
                EntityCommand<User> loginCommand = new LoginCommand(user);
                
                ChannelFuture future = channel.writeAndFlush(loginCommand);
                future.sync();
                                
                return null;
            }

            @Override
            protected void failed() 
            {
                Throwable exception = getException();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Client");
                alert.setHeaderText(exception.getClass().getName());
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
                
                Condition.setLogged(false);
                Condition.setConnected(false);
            }
        };
        
        new Thread(task).start();
    }
    
    @Override
    public void disconnect()
    {
        Condition.setLogged(false);
        
        if (!Condition.isConnected())
            return;
        
        Condition.setConnected(false);

        Task<Void> task = new Task<Void>() 
        {
            @Override
            protected Void call() throws Exception
            {
                channel.close().sync();
                bootstrap.config().group().shutdownGracefully().sync();
                                
                return null;
            }

            @Override
            protected void failed() 
            {
                Throwable exception = getException();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Client");
                alert.setHeaderText(exception.getClass().getName());
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        };
        
        new Thread(task).start();
    }
    
    @Override
    public void sendCommand(final Command command)
    {
        if (!Condition.isReady())
            return;
     
        Task<Void> task = new Task<Void>() 
        {
            @Override
            protected Void call() throws Exception
            {                
                ChannelFuture future = channel.writeAndFlush(command);
                future.sync();
                
                return null;
            }
            
            @Override
            protected void failed() 
            {                
                Throwable exception = getException();
                
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Client");
                alert.setHeaderText(exception.getClass().getName());
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
                
                Condition.setConnected(false);
                Condition.setLogged(false);
                
                reconnect();
            }
        };
        
        new Thread(task).start();
    }

    @Override
    public void acess(boolean value)
    {
        Condition.setLogged(value);        
    }

    @Override
    public BooleanProperty getAcess()
    {
        return Condition.getLogged();
    }
}

