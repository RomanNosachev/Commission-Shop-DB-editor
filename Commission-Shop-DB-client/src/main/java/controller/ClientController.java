package controller;

import java.math.BigDecimal;
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
import utils.comboBoxConverter.CompanyConverter;
import utils.comboBoxConverter.DistrictConverter;
import utils.comboBoxConverter.ProductGroupConverter;
import utils.comboBoxConverter.SocialStatusConverter;
import utils.tableColumnBuilder.CommittentTableColumnBuilder;
import utils.tableColumnBuilder.CompanyTableColumnBuilder;
import utils.tableColumnBuilder.DealTableColumnBuilder;
import utils.tableColumnBuilder.DistrictTableColumnBuilder;
import utils.tableColumnBuilder.ProductGroupTableColumnBuilder;
import utils.tableColumnBuilder.ProductImportTableColumnBuilder;
import utils.tableColumnBuilder.ProductTableColumnBuilder;
import utils.tableColumnBuilder.SocialStatusTableColumnBuilder;

public class ClientController 
{                
    private AbstractNetworkController networkController;
    
    private Map<String, EntityController<? extends DB_Entity>> tabMap;
    private EntityController<? extends DB_Entity> currentEntityController;
    
    //TODO
    private DB_Entity   buffer;
    
    @FXML
    private Button      refreshButton;
    @FXML
    private Button      refreshDirButton;
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
    private TextField               committentUpdateIdField;
    @FXML
    private TextField               committentUpdateNameField;
    @FXML
    private TextField               committentUpdateSurnameField;
    @FXML
    private TextField               committentUpdatePatronymicField;
    @FXML
    private ComboBox<District>      committentUpdateDistrictIdComboBox;
    @FXML
    private ComboBox<SocialStatus>  committentUpdateSocialStatusIdComboBox;
    @FXML
    private ComboBox<Company>       committentUpdateCompaniesIdComboBox;
    @FXML
    private DatePicker              committentUpdateDatePicker;
    @FXML
    private TextField               committentUpdateTelephoneNumberField;
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
    private TextField               productUpdateIdField;
    @FXML
    private ComboBox<ProductGroup>  productUpdateProductGroupComboBox;
    @FXML
    private TextField               productUpdateNameField;
    @FXML
    private ComboBox<ProductGroup>  productProductGroupComboBox;
    @FXML
    private TextField               productNameField;
    @FXML
    private TextField               productFindByIdField;
    @FXML
    private TextField               productFindByNameField;
    @FXML
    private TextField               productDeleteByIdField;
    
    @FXML
    private TextField               productImportUpdateIdField;
    @FXML
    private TextField               productImportUpdateProductIdField;
    @FXML
    private TextField               productImportUpdateReceiptNumberField;
    @FXML
    private DatePicker              productImportUpdateDatePicker;
    @FXML
    private TextField               productImportUpdateCountField;
    @FXML
    private TextField               productImportUpdatePriceField;
    @FXML
    private TextField               productImportProductIdField;
    @FXML
    private TextField               productImportReceiptNumberField;
    @FXML
    private DatePicker              productImportDatePicker;
    @FXML
    private TextField               productImportCountField;
    @FXML
    private TextField               productImportPriceField;
    @FXML
    private TextField               productImportFindByIdField;
    @FXML
    private TextField               productImportFindByNameField;
    @FXML
    private TextField               productImportFindByReceiptNumberField;
    @FXML
    private DatePicker              productImportFindByDatePicker;
    @FXML
    private TextField               productImportDeleteByIdField;
    
    @FXML
    private TextField               dealUpdateIdField;
    @FXML
    private TextField               dealUpdateProductIdField;
    @FXML
    private TextField               dealUpdateCommittentIdField;
    @FXML
    private DatePicker              dealUpdateDatePicker;
    @FXML
    private TextField               dealUpdateCountField;
    @FXML
    private TextField               dealUpdatePriceField;
    @FXML
    private TextField               dealProductIdField;
    @FXML
    private TextField               dealCommittentIdField;
    @FXML
    private DatePicker              dealDatePicker;
    @FXML
    private TextField               dealCountField;
    @FXML
    private TextField               dealPriceField;
    @FXML
    private TextField               dealFindByIdField;
    @FXML
    private TextField               dealFindByNameField;
    @FXML
    private TextField               dealFindByCommittentIdField;
    @FXML
    private DatePicker              dealFindByDatePicker;
    @FXML
    private TextField               dealDeleteByIdField;
    
    @FXML
    private TextField               companyUpdateIdField;
    @FXML
    private TextField               companyIdField;
    @FXML
    private TextField               companyDeleteByIdField;
    
    @FXML
    private TextField               districtUpdateIdField;
    @FXML
    private TextField               districtIdField;
    @FXML
    private TextField               districtDeleteByIdField;
    
    @FXML
    private TextField               socialStatusUpdateIdField;
    @FXML
    private TextField               socialStatusIdField;
    @FXML
    private TextField               socialStatusDeleteByIdField;
    
    @FXML
    private TextField               productGroupUpdateIdField;
    @FXML
    private TextField               productGroupIdField;
    @FXML
    private TextField               productGroupDeleteByIdField;
    
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
        companyComboBoxes.add(committentUpdateCompaniesIdComboBox);
        
        EntityController<Company> companyController = new DirectoryController<>(Company.class, companyTableView, sheetField, 
                companyComboBoxes)
                .columnBuilder(new CompanyTableColumnBuilder());
        
        List<ComboBox<District>> districtBoxes = new ArrayList<ComboBox<District>>();
        districtBoxes.add(committentDistrictIdComboBox);
        districtBoxes.add(committentUpdateDistrictIdComboBox);
        
        EntityController<District> districtController = new DirectoryController<>(District.class, districtTableView, sheetField, 
                districtBoxes)
                .columnBuilder(new DistrictTableColumnBuilder());
        
        List<ComboBox<ProductGroup>> productGroupComboBoxes = new ArrayList<ComboBox<ProductGroup>>();
        productGroupComboBoxes.add(productProductGroupComboBox);
        productGroupComboBoxes.add(productUpdateProductGroupComboBox);
        
        EntityController<ProductGroup> productGroupController = new DirectoryController<>(ProductGroup.class, productGroupTableView, sheetField,
                productGroupComboBoxes)
                .columnBuilder(new ProductGroupTableColumnBuilder());
        
        List<ComboBox<SocialStatus>> socialStatusComboBoxes = new ArrayList<ComboBox<SocialStatus>>();
        socialStatusComboBoxes.add(committentSocialStatusIdComboBox);
        socialStatusComboBoxes.add(committentUpdateSocialStatusIdComboBox);
        
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
        
        Image refreshImage = new Image(getClass().getResourceAsStream("/icons/refresh.png"));
        refreshButton.setGraphic(new ImageView(refreshImage));
        Image refreshDirImage = new Image(getClass().getResourceAsStream("/icons/refresh_dir.png"));
        refreshDirButton.setGraphic(new ImageView(refreshDirImage));
        
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
                    findCommittent();
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
        
        StringConverter<Company> companyConverter = new CompanyConverter();
        
        committentCompaniesIdComboBox.setConverter(companyConverter);
        committentUpdateCompaniesIdComboBox.setConverter(companyConverter);
        
        StringConverter<SocialStatus> socialStatusConverter = new SocialStatusConverter();
        
        committentSocialStatusIdComboBox.setConverter(socialStatusConverter);
        committentUpdateSocialStatusIdComboBox.setConverter(socialStatusConverter);
 
        StringConverter<District> districtConverter = new DistrictConverter();
        
        committentDistrictIdComboBox.setConverter(districtConverter);
        committentUpdateDistrictIdComboBox.setConverter(districtConverter);
        
        StringConverter<ProductGroup> productGroupConverter = new ProductGroupConverter();
        
        productProductGroupComboBox.setConverter(productGroupConverter);
        productUpdateProductGroupComboBox.setConverter(productGroupConverter);
        
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
            String telephoneNumber = committentTelephoneNumberField.getText();
            
            district = committentDistrictIdComboBox.getSelectionModel().getSelectedItem();            
            status = committentSocialStatusIdComboBox.getSelectionModel().getSelectedItem();
            
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
                                
            Committent committent = new Committent(name, surname, patronymic, district, status, companies, date, telephoneNumber);
            
            networkController.sendCommand(new CreateCommand<Committent>(committent));
        }
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void findCommittent()
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
            long id = Long.parseLong(committentUpdateIdField.getText());
            String name = committentUpdateNameField.getText();
            String surname = committentUpdateSurnameField.getText();
            String patronymic = committentUpdatePatronymicField.getText();
            
            District district = null;
            SocialStatus status = null;
            String[] companiesId;
            List<Company> companies = null;
            LocalDate localDate = null;
            Date date = null;
            String telephoneNumber = committentUpdateTelephoneNumberField.getText();
            
            district = committentUpdateDistrictIdComboBox.getSelectionModel().getSelectedItem();            
            status = committentUpdateSocialStatusIdComboBox.getSelectionModel().getSelectedItem();
            
            if (committentUpdateCompaniesIdComboBox.getSelectionModel().getSelectedItem() != null)
            {
                companies = new ArrayList<>();
                companiesId = committentUpdateCompaniesIdComboBox.getSelectionModel().getSelectedItem().getName().split(",");
                
                for (String companyId : companiesId)
                    companies.add(new Company(companyId));
            }

            if (committentUpdateDatePicker.getValue() != null)
            {
                localDate = committentUpdateDatePicker.getValue();
            
                Calendar c =  Calendar.getInstance();
                c.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
                date = c.getTime();
            }
                                
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
        try
        {
            ProductGroup productGroup = null;
            String name = productNameField.getText();
            
            productGroup = productProductGroupComboBox.getSelectionModel().getSelectedItem();
            
            Product product = new Product(productGroup, name);
            
            networkController.sendCommand(new CreateCommand<>(product));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void findProduct()
    {
        try
        {
            String text = productFindByIdField.getText();
            
            networkController.sendCommand(new FindCommand<>(Product.class, Long.parseLong(text)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void updateProduct()
    {
        try
        {
            long id = Long.parseLong(productUpdateIdField.getText());
            ProductGroup productGroup = null;
            String name = productUpdateNameField.getText();
            
            productGroup = productUpdateProductGroupComboBox.getSelectionModel().getSelectedItem();
            
            Product product = new Product(productGroup, name);
            product.setId(id);
            
            networkController.sendCommand(new UpdateCommand<>(product));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void deleteProduct()
    {
        try
        {
            String text = productDeleteByIdField.getText();
            
            networkController.sendCommand(new RemoveCommand<>(Product.class, Long.parseLong(text)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void addProductImport()
    {
        try
        {
            Product product = new Product();
            long receiptNumber = Long.parseLong(productImportReceiptNumberField.getText());
            Date date = null;
            int count = Integer.parseInt(productImportCountField.getText());
            BigDecimal price = null;
            LocalDate localDate = null;
            
            product.setId(Long.parseLong(productImportProductIdField.getText()));
            
            if (productImportDatePicker.getValue() != null)
            {
                localDate = productImportDatePicker.getValue();
            
                Calendar c =  Calendar.getInstance();
                c.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
                date = c.getTime();
            }
            
            price = new BigDecimal(productImportPriceField.getText());
            
            ProductImport productImport = new ProductImport(product, receiptNumber, date, count, price);
            
            networkController.sendCommand(new CreateCommand<>(productImport));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void findProductImport()
    {
        String text = productImportFindByIdField.getText();
        
        try
        {
            networkController.sendCommand(new FindCommand<>(ProductImport.class, Long.parseLong(text)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void updateProductImport()
    {
        try
        {
            long id = Long.parseLong(productImportUpdateIdField.getText());
            Product product = null;
            long receiptNumber = 0;
            Date date = null;
            int count = 0;
            BigDecimal price = null;
            LocalDate localDate = null;
            
            if (!productImportUpdateProductIdField.getText().isEmpty())
            {
                product = new Product();
                product.setId(Long.parseLong(productImportUpdateProductIdField.getText()));
            }
            
            if (!productImportUpdateReceiptNumberField.getText().isEmpty())
                receiptNumber = Long.parseLong(productImportUpdateReceiptNumberField.getText());
            
            if (!productImportUpdateCountField.getText().isEmpty())
                count = Integer.parseInt(productImportUpdateCountField.getText());
            
            if (productImportUpdateDatePicker.getValue() != null)
            {
                localDate = productImportUpdateDatePicker.getValue();
            
                Calendar c =  Calendar.getInstance();
                c.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
                date = c.getTime();
            }
            
            if (!productImportUpdatePriceField.getText().isEmpty())
                price = new BigDecimal(productImportUpdatePriceField.getText());
            
            ProductImport productImport = new ProductImport(product, receiptNumber, date, count, price);
            productImport.setId(id);
            
            networkController.sendCommand(new UpdateCommand<>(productImport));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void deleteProductImport()
    {
        String text = productImportDeleteByIdField.getText();
        
        try
        {
            networkController.sendCommand(new RemoveCommand<>(ProductImport.class, Long.parseLong(text)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void addDeal()
    {
        try
        {
            Product product = new Product();
            Committent committent = new Committent();
            Date date = null;
            int count = Integer.parseInt(dealCountField.getText());
            BigDecimal price = new BigDecimal(dealPriceField.getText());
            LocalDate localDate;
            
            product.setId(Long.parseLong(dealProductIdField.getText()));
            committent.setId(Long.parseLong(dealCommittentIdField.getText()));
            
            if (dealDatePicker.getValue() != null)
            {
                localDate = dealDatePicker.getValue();
            
                Calendar c =  Calendar.getInstance();
                c.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
                date = c.getTime();
            }
            
            Deal deal = new Deal(product, committent, date, count, price);
            
            networkController.sendCommand(new CreateCommand<>(deal));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void findDeal()
    {
        String text = dealFindByIdField.getText();
        
        try
        {
            networkController.sendCommand(new FindCommand<>(Deal.class, Long.parseLong(text)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void updateDeal()
    {
        try
        {
            long id = Long.parseLong(dealUpdateIdField.getText());
            Product product = new Product();
            Committent committent = new Committent();
            Date date = null;
            int count = Integer.parseInt(dealUpdateCountField.getText());
            BigDecimal price = new BigDecimal(dealUpdatePriceField.getText());
            LocalDate localDate;
            
            product.setId(Long.parseLong(dealUpdateProductIdField.getText()));
            committent.setId(Long.parseLong(dealUpdateCommittentIdField.getText()));
            
            if (dealUpdateDatePicker.getValue() != null)
            {
                localDate = dealUpdateDatePicker.getValue();
            
                Calendar c =  Calendar.getInstance();
                c.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
                date = c.getTime();
            }
            
            Deal deal = new Deal(product, committent, date, count, price);
            deal.setId(id);
            
            networkController.sendCommand(new UpdateCommand<>(deal));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void deleteDeal()
    {
        String text = dealDeleteByIdField.getText();
        
        try
        {
            networkController.sendCommand(new RemoveCommand<>(Deal.class, Long.parseLong(text)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void addCompany()
    {
        Company company = new Company(companyIdField.getText());
        
        networkController.sendCommand(new CreateCommand<>(company));
    }
    
    @FXML
    public void updateCompany()
    {
        Company company = new Company(companyUpdateIdField.getText());
        
        networkController.sendCommand(new UpdateCommand<>(company));
    }
    
    @FXML
    public void deleteCompany()
    {
        String name = companyDeleteByIdField.getText();
        
        networkController.sendCommand(new RemoveCommand<>(Company.class, name));
    }
    
    @FXML
    public void addDistrict()
    {
        District district = new District(districtIdField.getText());
        
        networkController.sendCommand(new CreateCommand<>(district));
    }
    
    @FXML
    public void updateDistrict()
    {
        District district = new District(districtUpdateIdField.getText());
        
        networkController.sendCommand(new UpdateCommand<>(district));
    }
    
    @FXML
    public void deleteDistrict()
    {
        String name = districtDeleteByIdField.getText();
        
        networkController.sendCommand(new RemoveCommand<>(District.class, name));
    }
    
    @FXML
    public void addSocialStatus()
    {
        SocialStatus socialStatus = new SocialStatus(socialStatusIdField.getText());
        
        networkController.sendCommand(new CreateCommand<>(socialStatus));
    }
    
    @FXML
    public void updateSocialStatus()
    {
        SocialStatus socialStatus = new SocialStatus(socialStatusUpdateIdField.getText());
        
        networkController.sendCommand(new UpdateCommand<>(socialStatus));
    }
    
    @FXML
    public void deleteSocialStatus()
    {
        String name = socialStatusDeleteByIdField.getText();
        
        networkController.sendCommand(new RemoveCommand<>(SocialStatus.class, name));
    }
    
    @FXML
    public void addProductGroup()
    {
        ProductGroup productGroup = new ProductGroup(productGroupIdField.getText());
        
        networkController.sendCommand(new CreateCommand<>(productGroup));
    }
    
    @FXML
    public void updateProductGroup()
    {
        ProductGroup productGroup = new ProductGroup(productGroupIdField.getText());
        
        networkController.sendCommand(new CreateCommand<>(productGroup));
    }
    
    @FXML
    public void deleteProductGroup()
    {
        String name = productGroupDeleteByIdField.getText();
        
        networkController.sendCommand(new RemoveCommand<>(ProductGroup.class, name));
    }
}
