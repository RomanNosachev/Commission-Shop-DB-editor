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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
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
    
    private Map<String, EntityController<? extends DB_Entity>> tabMap;
    private EntityController<? extends DB_Entity> currentEntityController;
    
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
    private TextField           committentNameField;
    @FXML
    private TextField           committentSurnameField;
    @FXML
    private TextField           committentPatronymicField;
    @FXML
    private ComboBox<String>    committentDistrictIdComboBox;
    @FXML
    private ComboBox<String>    committentSocialStatusIdComboBox;
    @FXML
    private ChoiceBox<String>   committentCompaniesIdChoiceBox;
    @FXML
    private DatePicker          committentDatePicker;
    @FXML
    private TextField           committentTelephoneNumberField;
    
    @FXML
    public void initialize()
    {                
        EntityController<Committent> committentController = new EntityController<>(Committent.class, committentTableView, sheetField)
                .columnBuilder(new CommittentTableColumnBuilder());
        EntityController<Deal> dealController = new EntityController<>(Deal.class, dealTableView, sheetField)
                .columnBuilder(new DealTableColumnBuilder());
        EntityController<Company> companyController = new EntityController<>(Company.class, companyTableView, sheetField)
                .columnBuilder(new CompanyTableColumnBuilder());
        EntityController<District> districtController = new EntityController<>(District.class, districtTableView, sheetField)
                .columnBuilder(new DistrictTableColumnBuilder());
        EntityController<ProductImport> productImportController = new EntityController<>(ProductImport.class, productImportTableView, sheetField)
                .columnBuilder(new ProductImportTableColumnBuilder());
        EntityController<ProductGroup> productGroupController = new EntityController<>(ProductGroup.class, productGroupTableView, sheetField)
                .columnBuilder(new ProductGroupTableColumnBuilder());
        EntityController<SocialStatus> socialStatusController =  new EntityController<>(SocialStatus.class, socialStatusTableView, sheetField)
                .columnBuilder(new SocialStatusTableColumnBuilder());
        EntityController<Product> productController = new EntityController<>(Product.class, productTableView, sheetField)
                .columnBuilder(new ProductTableColumnBuilder());
        
        currentEntityController = committentController;
        
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
                
                pipeline.addLast(new FindAllResponceHandler<>(committentController));
                pipeline.addLast(new FindAllResponceHandler<>(companyController));
                pipeline.addLast(new FindAllResponceHandler<>(dealController));
                pipeline.addLast(new FindAllResponceHandler<>(districtController));
                pipeline.addLast(new FindAllResponceHandler<>(productController));
                pipeline.addLast(new FindAllResponceHandler<>(productGroupController));
                pipeline.addLast(new FindAllResponceHandler<>(productImportController));
                pipeline.addLast(new FindAllResponceHandler<>(socialStatusController));
                
                pipeline.addLast(new ClientDefaultHandler());
            }
        });
        
        Image image = new Image(getClass().getResourceAsStream("/icons/refresh.png"));
        refreshButton.setGraphic(new ImageView(image));
        
        connectPane.disableProperty().bind(networkController.getAcess());
        connectPane.visibleProperty().bind(networkController.getAcess().not());
        disconnectPane.disableProperty().bind(networkController.getAcess().not());
        disconnectPane.visibleProperty().bind(networkController.getAcess());
                
        sheetField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event)
            {
                if (event.getCode().equals(KeyCode.ENTER))
                    onSheetNumberChanged();
            }
        });
            
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue)
            {
                currentEntityController = tabMap.get(newValue.getText());
                      
                sheetField.setText(String.valueOf(currentEntityController.getSheet()));
            }
        });
        
        tabMap = new HashMap<>();
        tabMap.put("Committent", committentController);
        tabMap.put("Product", productController);
        tabMap.put("Import", productImportController);
        tabMap.put("Deal", dealController);
        tabMap.put("Company", companyController);
        tabMap.put("District", districtController);
        tabMap.put("Social status", socialStatusController);
        tabMap.put("Product group", productGroupController);
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
    public void addCommittent()
    {        
        String name = committentNameField.getText();
        String surname = committentSurnameField.getText();
        String patronymic = committentPatronymicField.getText();
        District district = new District(committentDistrictIdComboBox.getSelectionModel().getSelectedItem().toString());
        SocialStatus status = new SocialStatus(committentSocialStatusIdComboBox.getSelectionModel().getSelectedItem().toString());
        
        List<Company> companies = new ArrayList<>();
        String[] companiesId = committentCompaniesIdChoiceBox.getSelectionModel().getSelectedItem().toString().split(",");
        
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
    public void addProduct()
    {
        
    }
    
    @FXML
    public void addDeal()
    {
        
    }
    
    @FXML
    public void addProductImport()
    {
        
    }
    
    @FXML
    public void addCompany()
    {
        
    }
    
    @FXML
    public void addDistrict()
    {
        
    }
    
    @FXML
    public void addSocialStatus()
    {
        
    }
    
    @FXML
    public void addProductGroup()
    {
        
    }
    
    @FXML
    public void refresh()
    {                
        Class<? extends DB_Entity> type = tabMap.get(getCurrentTabName()).getEntityClass();                
        networkController.sendCommand(new FindAllCommand<>(type, FetchMode.EAGER, currentEntityController.getSheet()));
    }
    
    @FXML
    public void nextSheet()
    {
        int currentSheet = currentEntityController.getSheet();
        int newSheet = currentSheet >= Integer.MAX_VALUE ? Integer.MAX_VALUE : currentSheet + 1;
        
        currentEntityController.setSheet(newSheet);
        refresh();
    }
    
    @FXML
    public void firstSheet()
    {        
        currentEntityController.setSheet(1);
        refresh();
    }
    
    @FXML
    public void lastSheet()
    {        
        currentEntityController.setSheet(Integer.MAX_VALUE);
        refresh();
    }
    
    @FXML
    public void prevSheet()
    {
        int currentSheet = currentEntityController.getSheet();
        int newSheet = currentSheet <= 1 ? 1 : currentSheet - 1;
        
        currentEntityController.setSheet(newSheet);
        refresh();
    }
    
    @FXML
    public void onSheetNumberChanged()
    {
        try
        {            
            currentEntityController.setSheet(Integer.valueOf(sheetField.getText()));
            refresh();
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
    
    private String getCurrentTabName()
    {
        return tabPane.getSelectionModel().getSelectedItem().getText();
    }
}
