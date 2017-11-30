package controller;

import command.FindAllCommand;
import dao.Committent;
import dao.Company;
import dao.Deal;
import dao.District;
import dao.Product;
import dao.ProductGroup;
import dao.ProductImport;
import dao.SocialStatus;
import inboundHandler.ClientDefaultHandler;
import inboundHandler.FindAllCommittentResponceHandler;
import inboundHandler.FindAllCompanyResponseHandler;
import inboundHandler.FindAllDistrictResponseHandler;
import inboundHandler.LoginResponseHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import outboundHandler.ClientHandler;
import utils.CommittentTableColumnBuilder;

public class ClientController 
{                
    private AbstractNetworkController networkController;
    
    @FXML
    private TableView<Committent>       committentTableView;
    @FXML
    private TableView<Deal>             dealTableView;
    @FXML
    private TableView<ProductImport>    productImportTableView;
    @FXML
    private TableView<Product>          productTableView;
    @FXML
    private TableView<Company>          companyTableView;
    @FXML
    private TableView<District>         districtTableView;
    @FXML
    private TableView<ProductGroup>     productGroupTableView;
    @FXML
    private TableView<SocialStatus>     socialStatusTableView;

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
        networkController = new ClientNetworkController(new ChannelInitializer<SocketChannel>() 
        {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception
            {
                ChannelPipeline pipeline = ch.pipeline();
                
                pipeline.addLast(new ObjectEncoder());
                pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                
                pipeline.addLast(new ClientHandler());
                
                pipeline.addLast(new LoginResponseHandler(networkController));
                pipeline.addLast(new FindAllCommittentResponceHandler(committentTableView));
                pipeline.addLast(new FindAllCompanyResponseHandler(companyTableView));
                pipeline.addLast(new FindAllDistrictResponseHandler(districtTableView));
                pipeline.addLast(new ClientDefaultHandler());
            }
        });
        
        connectPane.disableProperty().bind(networkController.getAcess());
        connectPane.visibleProperty().bind(networkController.getAcess().not());
        disconnectPane.disableProperty().bind(networkController.getAcess().not());
        disconnectPane.visibleProperty().bind(networkController.getAcess());
                
        committentTableView.getColumns().addAll(new CommittentTableColumnBuilder().buildTableColumns());
    }
    
    @FXML
    public void connect()
    {        
        networkController.connect(loginField.getText(), passField.getText());
    }
    
    @FXML
    public void disconnect()
    {
        networkController.disconnect();
    }

    @FXML
    public void refresh()
    {
        networkController.sendCommand(new FindAllCommand<>(Committent.class));
    }
    
    public void shutdown()
    {        
        networkController.disconnect();
    }
}
