package controller;

import command.EntityCommand;
import command.FindAllCommand;
import command.LoginCommand;
import dao.Company;
import dao.DB_Entity;
import dao.User;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ClientController 
{    
    private String  host = System.getProperty("host", "127.0.0.1");
    private int     port = Integer.parseInt(System.getProperty("port", "8007"));
    
    private String  login;
    private String  password;
    
    private Channel         channel;
    private Bootstrap       bootstrap;
    private EventLoopGroup  group;
            
    public static class Condition
    {
        private static volatile BooleanProperty connected = new SimpleBooleanProperty(false);
        private static volatile BooleanProperty logged = new SimpleBooleanProperty(false);
        
        public static synchronized void setConnected(boolean state)
        {
            connected.set(state);
        }
        
        public static synchronized void setLogged(boolean state)
        {
            logged.set(state);
        }
        
        public static synchronized boolean isConnected()
        {
            return connected.get();
        }
        
        public static synchronized boolean isLogged()
        {
            return logged.get();
        }
        
        public static synchronized boolean isReady()
        {
            return connected.get() && logged.get();
        }
    }
    
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
    private TableView<? extends DB_Entity>    tableView;
        
    @FXML
    public void initialize()
    {        
        connectPane.disableProperty().bind(Condition.logged);
        connectPane.visibleProperty().bind(Condition.logged.not());
        disconnectPane.disableProperty().bind(Condition.logged.not());
        disconnectPane.visibleProperty().bind(Condition.logged);
    }
    
    @FXML
    public void connect()
    {        
        group = new NioEventLoopGroup();
        final ClientController controller = this;
        
        Task<Channel> task = new Task<Channel>() 
        {
            @Override
            protected Channel call() throws Exception
            {
                bootstrap = new Bootstrap();
                bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .remoteAddress(host, port)
                    .handler(new ClientChannelInitializer(controller)); 
                        
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
                
                login();
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
                
                login();
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
    
    @FXML
    public void login()
    {
        if (!Condition.isConnected())
            return;
        
        Task<Void> task = new Task<Void>() 
        {
            @Override
            protected Void call() throws Exception
            {
                login = loginField.getText();
                password = passField.getText();
                
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
    
    @FXML
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
                group.shutdownGracefully().sync();
                                
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
    
    @FXML
    public void test()
    {
        if (!Condition.isReady())
            return;
     
        Task<Void> task = new Task<Void>() 
        {
            @Override
            protected Void call() throws Exception
            {                
                ChannelFuture future = channel.writeAndFlush(new FindAllCommand<Company>(Company.class));
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

    public void shutdown()
    {
        disconnect();
    }
}
