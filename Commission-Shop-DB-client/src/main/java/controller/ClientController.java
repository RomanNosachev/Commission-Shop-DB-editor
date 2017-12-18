package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import command.CreateCommand;
import command.FetchMode;
import command.FindAllCommand;
import dao.Committent;
import dao.Company;
import dao.DB_Entity;
import dao.Deal;
import dao.District;
import dao.Product;
import dao.ProductGroup;
import dao.ProductImport;
import dao.SocialStatus;
import inboundHandler.ClientDefaultHandler;
import inboundHandler.FindAllResponceHandler;
import inboundHandler.LoginResponseHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.converter.NumberStringConverter;
import outboundHandler.ClientHandler;
import utils.CommittentTableColumnBuilder;
import utils.CompanyTableColumnBuilder;
import utils.DealTableColumnBuilder;
import utils.DistrictTableColumnBuilder;
import utils.ProductGroupTableColumnBuilder;
import utils.ProductImportTableColumnBuilder;
import utils.ProductTableColumnBuilder;
import utils.SocialStatusTableColumnBuilder;

public class ClientController 
{                
    private AbstractNetworkController networkController;
    
    private Map<String, Class<? extends DB_Entity>> tabMap;
    
    private IntegerProperty sheetProperty = new SimpleIntegerProperty(1);
    
    @FXML
    private Button      refreshButton;
    @FXML
    private TextField   sheetField;
    
    @FXML
    private TabPane                     tabPane;
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
    private TextField       committentNameField;
    @FXML
    private TextField       committentSurnameField;
    @FXML
    private TextField       committentPatronymicField;
    @FXML
    private TextField       committentDistrictIdField;
    @FXML
    private TextField       committentSocialStatusIdField;
    @FXML
    private TextField       committentCompaniesIdField;
    @FXML
    private DatePicker      committentDatePicker;
    @FXML
    private TextField       committentTelephoneNumberField;
    
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
                
                pipeline.addLast(new FindAllResponceHandler<>(Committent.class, committentTableView));
                pipeline.addLast(new FindAllResponceHandler<>(Company.class, companyTableView));
                pipeline.addLast(new FindAllResponceHandler<>(Deal.class, dealTableView));
                pipeline.addLast(new FindAllResponceHandler<>(District.class, districtTableView));
                pipeline.addLast(new FindAllResponceHandler<>(Product.class, productTableView));
                pipeline.addLast(new FindAllResponceHandler<>(ProductGroup.class, productGroupTableView));
                pipeline.addLast(new FindAllResponceHandler<>(ProductImport.class, productImportTableView));
                pipeline.addLast(new FindAllResponceHandler<>(SocialStatus.class, socialStatusTableView));
                
                pipeline.addLast(new ClientDefaultHandler());
            }
        });
        
        Image image = new Image(getClass().getResourceAsStream("/icons/refresh.png"));
        refreshButton.setGraphic(new ImageView(image));
        
        connectPane.disableProperty().bind(networkController.getAcess());
        connectPane.visibleProperty().bind(networkController.getAcess().not());
        disconnectPane.disableProperty().bind(networkController.getAcess().not());
        disconnectPane.visibleProperty().bind(networkController.getAcess());
                
        sheetField.textProperty().bindBidirectional(sheetProperty, new NumberStringConverter());
        sheetField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event)
            {
                if (event.getCode().equals(KeyCode.ENTER))
                    onSheetNumberChanged();
            }
        });
        
        committentTableView.getColumns().addAll(new CommittentTableColumnBuilder().buildTableColumns());
        companyTableView.getColumns().addAll(new CompanyTableColumnBuilder().buildTableColumns());
        dealTableView.getColumns().addAll(new DealTableColumnBuilder().buildTableColumns());
        districtTableView.getColumns().addAll(new DistrictTableColumnBuilder().buildTableColumns());
        productGroupTableView.getColumns().addAll(new ProductGroupTableColumnBuilder().buildTableColumns());
        productImportTableView.getColumns().addAll(new ProductImportTableColumnBuilder().buildTableColumns());
        productTableView.getColumns().addAll(new ProductTableColumnBuilder().buildTableColumns());
        socialStatusTableView.getColumns().addAll(new SocialStatusTableColumnBuilder().buildTableColumns());
        
        tabMap = new HashMap<>();
        tabMap.put("Committent", Committent.class);
        tabMap.put("Product", Product.class);
        tabMap.put("Import", ProductImport.class);
        tabMap.put("Deal", Deal.class);
        tabMap.put("Company", Company.class);
        tabMap.put("District", District.class);
        tabMap.put("Social status", SocialStatus.class);
        tabMap.put("Product group", ProductGroup.class);
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
    public void add()
    {        
        String name = committentNameField.getText();
        String surname = committentSurnameField.getText();
        String patronymic = committentPatronymicField.getText();
        District district = new District(committentDistrictIdField.getText());
        SocialStatus status = new SocialStatus(committentSocialStatusIdField.getText());
        
        List<Company> companies = new ArrayList<>();
        String[] companiesId = committentCompaniesIdField.getText().split(",");
        
        for (String companyId : companiesId)
            companies.add(new Company(companyId));
        
        LocalDate localDate = committentDatePicker.getValue();
        Calendar c =  Calendar.getInstance();
        c.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        Date date = c.getTime();
        String telephoneNumber = committentTelephoneNumberField.getText();
                
        Committent committent = new Committent(name, surname, patronymic, district, status, companies, date, telephoneNumber);
        
        networkController.sendCommand(new CreateCommand<Committent>(committent));
    }
    
    @FXML
    public void refresh()
    {        
        Class<? extends DB_Entity> type = tabMap.get(tabPane.getSelectionModel().getSelectedItem().getText());
                
        networkController.sendCommand(new FindAllCommand<>(type, FetchMode.EAGER, sheetProperty.get()));
    }
    
    @FXML
    public void nextSheet()
    {
        int sheet = sheetProperty.greaterThanOrEqualTo(Integer.MAX_VALUE).get() ? Integer.MAX_VALUE : sheetProperty.get() + 1;
        sheetProperty.set(sheet);
        refresh();
    }
    
    @FXML
    public void firstSheet()
    {
        sheetProperty.set(1);
        refresh();
    }
    
    @FXML
    public void lastSheet()
    {
        sheetProperty.set(Integer.MAX_VALUE);
        refresh();
    }
    
    @FXML
    public void prevSheet()
    {
        int sheet = sheetProperty.lessThanOrEqualTo(1).get() ? 1 : sheetProperty.get() - 1;
        sheetProperty.set(sheet);
        refresh();
    }
    
    @FXML
    public void onSheetNumberChanged()
    {
        try
        {
            sheetProperty.set(Integer.valueOf(sheetField.getText()));
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
    }
    
    public void shutdown()
    {        
        networkController.disconnect();
    }
}
