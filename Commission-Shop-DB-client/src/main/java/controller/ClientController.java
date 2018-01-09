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
import command.FindCommand;
import command.RemoveCommand;
import command.UpdateCommand;
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
import inboundHandler.FindResponceHandler;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
import javafx.util.StringConverter;
import outboundHandler.ClientOutboundHandler;
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
    
    private DB_Entity   buffer;
    
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
    private Label           userStatusLabel;
        
    @FXML
    private TextField               committentSearchIdField;
    @FXML
    private TextField               committentSearchNameField;
    @FXML
    private TextField               committentSearchSurnameField;
    @FXML
    private TextField               committentSearchPatronymicField;
    @FXML
    private ComboBox<District>      committentSearchDistrictIdComboBox;
    @FXML
    private ComboBox<SocialStatus>  committentSearchSocialStatusIdComboBox;
    @FXML
    private ComboBox<Company>       committentSearchCompaniesIdComboBox;
    @FXML
    private DatePicker              committentSearchDatePicker;
    @FXML
    private TextField               committentSearchTelephoneNumberField;
    @FXML
    private TextField               committentNameField;
    @FXML
    private TextField               committentSurnameField;
    @FXML
    private TextField               committentPatronymicField;
    @FXML
    private ComboBox<District>      committentDistrictIdComboBox;
    @FXML
    private ComboBox<SocialStatus>  committentSocialStatusIdComboBox;
    @FXML
    private ComboBox<Company>       committentCompaniesIdComboBox;
    @FXML
    private DatePicker              committentDatePicker;
    @FXML
    private TextField               committentTelephoneNumberField;
    @FXML
    private TextField               committentFindByIdField;
    @FXML
    private TextField               committentFindBySurnameField;
    @FXML
    private TextField               committentDeleteField;
    
    @FXML
    public void initialize()
    {                                
        EntityController<Committent> committentController = new EntityController<>(Committent.class, committentTableView, sheetField)
                .columnBuilder(new CommittentTableColumnBuilder());
        EntityController<Product> productController = new EntityController<>(Product.class, productTableView, sheetField)
                .columnBuilder(new ProductTableColumnBuilder());
        EntityController<ProductImport> productImportController = new EntityController<>(ProductImport.class, productImportTableView, sheetField)
                .columnBuilder(new ProductImportTableColumnBuilder());
        EntityController<Deal> dealController = new EntityController<>(Deal.class, dealTableView, sheetField)
                .columnBuilder(new DealTableColumnBuilder());
        
        List<ComboBox<Company>> companyComboBoxes = new ArrayList<ComboBox<Company>>();
        companyComboBoxes.add(committentCompaniesIdComboBox);
        companyComboBoxes.add(committentSearchCompaniesIdComboBox);
        
        EntityController<Company> companyController = new DirectoryController<>(Company.class, companyTableView, sheetField, 
                companyComboBoxes)
                .columnBuilder(new CompanyTableColumnBuilder());
        
        List<ComboBox<District>> districtBoxes = new ArrayList<ComboBox<District>>();
        districtBoxes.add(committentDistrictIdComboBox);
        districtBoxes.add(committentSearchDistrictIdComboBox);
        
        EntityController<District> districtController = new DirectoryController<>(District.class, districtTableView, sheetField, 
                districtBoxes)
                .columnBuilder(new DistrictTableColumnBuilder());
        
        List<ComboBox<ProductGroup>> productProupComboBoxes = new ArrayList<ComboBox<ProductGroup>>();
        productProupComboBoxes.add(new ComboBox<>());
        
        EntityController<ProductGroup> productGroupController = new DirectoryController<>(ProductGroup.class, productGroupTableView, sheetField,
                productProupComboBoxes)
                .columnBuilder(new ProductGroupTableColumnBuilder());
        
        List<ComboBox<SocialStatus>> socialStatusComboBoxes = new ArrayList<ComboBox<SocialStatus>>();
        socialStatusComboBoxes.add(committentSocialStatusIdComboBox);
        socialStatusComboBoxes.add(committentSearchSocialStatusIdComboBox);
        
        EntityController<SocialStatus> socialStatusController =  new DirectoryController<>(SocialStatus.class, socialStatusTableView, sheetField,
                socialStatusComboBoxes)
                .columnBuilder(new SocialStatusTableColumnBuilder());
       
        currentEntityController = committentController;
        
        networkController = new ClientNetworkController(new ChannelInitializer<SocketChannel>() 
        {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception
            {                
                ChannelPipeline pipeline = ch.pipeline();
                
                pipeline.addFirst(new ObjectEncoder());
                pipeline.addFirst(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                
                pipeline.addLast(new ClientOutboundHandler());
                
                pipeline.addLast(new LoginResponseHandler(networkController));
                
                pipeline.addLast(new FindAllResponceHandler<>(committentController));
                pipeline.addLast(new FindAllResponceHandler<>(companyController));
                pipeline.addLast(new FindAllResponceHandler<>(dealController));
                pipeline.addLast(new FindAllResponceHandler<>(districtController));
                pipeline.addLast(new FindAllResponceHandler<>(productController));
                pipeline.addLast(new FindAllResponceHandler<>(productGroupController));
                pipeline.addLast(new FindAllResponceHandler<>(productImportController));
                pipeline.addLast(new FindAllResponceHandler<>(socialStatusController));
                
                pipeline.addLast(new FindResponceHandler<>(committentController));
                pipeline.addLast(new FindResponceHandler<>(companyController));
                pipeline.addLast(new FindResponceHandler<>(dealController));
                pipeline.addLast(new FindResponceHandler<>(districtController));
                pipeline.addLast(new FindResponceHandler<>(productController));
                pipeline.addLast(new FindResponceHandler<>(productImportController));
                pipeline.addLast(new FindResponceHandler<>(socialStatusController));
                pipeline.addLast(new FindResponceHandler<>(productGroupController));
             
                pipeline.addLast(new ClientDefaultHandler());
            }
        });
        
        Image image = new Image(getClass().getResourceAsStream("/icons/refresh.png"));
        refreshButton.setGraphic(new ImageView(image));
        
        connectPane.disableProperty().bind(networkController.getAccess());
        connectPane.visibleProperty().bind(networkController.getAccess().not());
        disconnectPane.disableProperty().bind(networkController.getAccess().not());
        disconnectPane.visibleProperty().bind(networkController.getAccess());
        userStatusLabel.textProperty().bind(networkController.getUserStatus().asString());   
        
        sheetField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event)
            {
                if (event.getCode().equals(KeyCode.ENTER))
                    onSheetNumberChanged();
            }
        });
        
        committentFindByIdField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event)
            {
                if (event.getCode().equals(KeyCode.ENTER))
                    findCommittentById();
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
        
        StringConverter<Company> companyConverter = new StringConverter<Company>() {         
            @Override
            public String toString(Company object)
            {
                return object.getName();
            }
            
            @Override
            public Company fromString(String name)
            {
                return new Company(name);
            }
        };
        
        committentCompaniesIdComboBox.setConverter(companyConverter);
        committentSearchCompaniesIdComboBox.setConverter(companyConverter);
        
        StringConverter<SocialStatus> socialStatusConverter = new StringConverter<SocialStatus>() {          
            @Override
            public String toString(SocialStatus object)
            {
                return object.getName();
            }
            
            @Override
            public SocialStatus fromString(String name)
            {
                return new SocialStatus(name);
            }
        };
        
        committentSocialStatusIdComboBox.setConverter(socialStatusConverter);
        committentSearchSocialStatusIdComboBox.setConverter(socialStatusConverter);
        
        StringConverter<District> districtConverter = new StringConverter<District>() {           
            @Override
            public String toString(District object)
            {
                return object.getName();
            }
            
            @Override
            public District fromString(String name)
            {
                return new District(name);
            }
        };
        
        committentDistrictIdComboBox.setConverter(districtConverter);
        committentSearchDistrictIdComboBox.setConverter(districtConverter);
                
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
    public void refreshDirectories()
    {
        networkController.sendCommands(
                new FindAllCommand<>(District.class),
                new FindAllCommand<>(SocialStatus.class),
                new FindAllCommand<>(Company.class),
                new FindAllCommand<>(ProductGroup.class));
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
    
    @FXML
    public void addCommittent()
    {    
        try
        {
            String name = committentNameField.getText();
            String surname = committentSurnameField.getText();
            String patronymic = committentPatronymicField.getText();
            District district = null;
            SocialStatus status = null;
            String[] companiesId;
            List<Company> companies = null;
            LocalDate localDate = null;
            Date date = null;
            
            if (committentDistrictIdComboBox.getSelectionModel().getSelectedItem() != null)
                district = new District(committentDistrictIdComboBox.getSelectionModel().getSelectedItem().getName());
            
            if (committentSocialStatusIdComboBox.getSelectionModel().getSelectedItem() != null)
                status = new SocialStatus(committentSocialStatusIdComboBox.getSelectionModel().getSelectedItem().getName());
            
            if (committentCompaniesIdComboBox.getSelectionModel().getSelectedItem() != null)
            {
                companies = new ArrayList<>();
                companiesId = committentCompaniesIdComboBox.getSelectionModel().getSelectedItem().getName().split(",");
                
                for (String companyId : companiesId)
                    companies.add(new Company(companyId));
            }

            if (committentDatePicker.getValue() != null)
            {
                localDate = committentDatePicker.getValue();
            
                Calendar c =  Calendar.getInstance();
                c.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
                date = c.getTime();
            }
            String telephoneNumber = committentTelephoneNumberField.getText();
                    
            Committent committent = new Committent(name, surname, patronymic, district, status, companies, date, telephoneNumber);
            
            networkController.sendCommand(new CreateCommand<Committent>(committent));
        }
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void findCommittentById()
    {
        String text = committentFindByIdField.getText();
        
        try
        {
            networkController.sendCommand(new FindCommand<>(Committent.class, Long.parseLong(text)));
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }       
    }
    
    @FXML
    public void updateCommittent()
    {
        try
        {
            long id = Long.parseLong(committentSearchIdField.getText());
            String name = committentSearchNameField.getText();
            String surname = committentSearchSurnameField.getText();
            String patronymic = committentSearchPatronymicField.getText();
            
            District district = null;
            SocialStatus status = null;
            String[] companiesId;
            List<Company> companies = null;
            LocalDate localDate = null;
            Date date = null;
            
            if (committentSearchDistrictIdComboBox.getSelectionModel().getSelectedItem() != null)
                district = new District(committentSearchDistrictIdComboBox.getSelectionModel().getSelectedItem().getName());
            
            if (committentSearchSocialStatusIdComboBox.getSelectionModel().getSelectedItem() != null)
                status = new SocialStatus(committentSearchSocialStatusIdComboBox.getSelectionModel().getSelectedItem().getName());
            
            if (committentSearchCompaniesIdComboBox.getSelectionModel().getSelectedItem() != null)
            {
                companies = new ArrayList<>();
                companiesId = committentSearchCompaniesIdComboBox.getSelectionModel().getSelectedItem().getName().split(",");
                
                for (String companyId : companiesId)
                    companies.add(new Company(companyId));
            }

            if (committentSearchDatePicker.getValue() != null)
            {
                localDate = committentSearchDatePicker.getValue();
            
                Calendar c =  Calendar.getInstance();
                c.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
                date = c.getTime();
            }
            
            String telephoneNumber = committentSearchTelephoneNumberField.getText();
                    
            Committent committent = new Committent(name, surname, patronymic, district, status, companies, date, telephoneNumber);
            committent.setId(id);
            
            networkController.sendCommand(new UpdateCommand<Committent>(committent));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void deleteCommittent()
    {
        String text = committentDeleteField.getText();
        
        try
        {
            networkController.sendCommand(new RemoveCommand<>(Committent.class, Long.parseLong(text)));
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }       
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
}
