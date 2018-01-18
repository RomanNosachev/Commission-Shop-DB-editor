package controller;

import command.Command;
import command.DisconnectCommand;
import command.LoginCommand;
import dao.User;
import dao.UserStatus;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
        private volatile static IntegerProperty status = new SimpleIntegerProperty(UserStatus.User.ordinal());

        private static synchronized void setConnected(boolean value)
        {
            connected.set(value);
        }
        
        private static synchronized void setLogged(boolean value)
        {
            logged.set(value);
        }
        
        private static synchronized void setUserStatus(int value)
        {
            status.set(value);
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
        
        public static IntegerProperty getUserStatus()
        {
            return status;
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
                showErrorMessage(getException());
                
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
                showErrorMessage(getException());
                
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
                
                ChannelFuture future = channel.writeAndFlush(new LoginCommand(user));
                future.sync();
                                
                return null;
            }
            
            @Override
            protected void failed() 
            {
                showErrorMessage(getException());
                
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
                sendCommand(new DisconnectCommand());
                
                channel.close().sync();
                bootstrap.config().group().shutdownGracefully().sync();
                                
                return null;
            }

            @Override
            protected void failed() 
            {
                showErrorMessage(getException());
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
                showErrorMessage(getException());
                
                Condition.setConnected(false);
                Condition.setLogged(false);
                
                reconnect();
            }
        };
        
        new Thread(task).start();
    }
    
    @Override
    public void sendCommands(Command... commands)
    {
        if (!Condition.isReady())
            return;
     
        Task<Void> task = new Task<Void>() 
        {
            @Override
            protected Void call() throws Exception
            {         
                for (Command command : commands)
                    channel.write(command);
                
                channel.flush();

                return null;
            }
            
            @Override
            protected void failed() 
            {                
                showErrorMessage(getException());
                
                Condition.setConnected(false);
                Condition.setLogged(false);
                
                reconnect();
            }
        };
        
        new Thread(task).start();
    }

    @Override
    public void setAccess(boolean value)
    {
        Condition.setLogged(value);        
    }
    
    @Override
    public BooleanProperty getAccess()
    {
        return Condition.getLogged();
    }
    
    public void setUserStatus(int status)
    {
        Condition.setUserStatus(status);
    }  
    
    public IntegerProperty getUserStatus()
    {
        return Condition.getUserStatus();
    }  
    
    public void showErrorMessage(Throwable exception)
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Client");
        alert.setHeaderText(exception.getClass().getName());
        alert.setContentText(exception.getMessage());
        alert.showAndWait();
    }
}

