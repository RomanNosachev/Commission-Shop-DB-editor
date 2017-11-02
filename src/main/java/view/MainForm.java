package view;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import dao.Committent;
import dao.Company;
import dao.Deal;
import dao.District;
import dao.Product;
import dao.ProductGroup;
import dao.ProductImport;
import dao.SocialStatus;
import dao.User;
import dao.UserStatus;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import servise.CommittentService;
import servise.CompanyService;
import servise.DealService;
import servise.DistrictService;
import servise.ProductGroupService;
import servise.ProductImportService;
import servise.ProductService;
import servise.Service;
import servise.SocialStatusService;
import servise.UserService;

public class MainForm 
extends Application 
{
    @FXML
    private Stage      primaryStage;
    @FXML
    private BorderPane rootLayout;
    @FXML
    private TextArea   area;
        
    private Service<Company> companyService;
    private Service<Committent> committentService;
    private Service<District> districtService;
    private Service<SocialStatus> socialStatusService;
    
    private Service<Deal> dealService;
    private Service<ProductGroup> productGroupService;
    private Service<ProductImport> productImportService;
    private Service<Product> productService;
    
    private Service<User> userService;

    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Comission Shop DB-editor");
        this.primaryStage.setWidth(800);
        this.primaryStage.setHeight(600);
        
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainForm.class.getResource("/fxml/RootLayout.fxml"));
            rootLayout = loader.load();
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainForm.class.getResource("/fxml/MainForm.fxml"));
            BorderPane view = loader.load();
            
            rootLayout.setCenter(view);
        } catch (IOException e)
        {
            e.printStackTrace();
        }        
    }
    
    public void main()
    {
        launch();
    }
    
    public void test()
    {                 
        try 
        {
            companyService = new CompanyService();
            committentService = new CommittentService();
            districtService = new DistrictService();
            socialStatusService = new SocialStatusService();
            
            dealService = new DealService();
            productGroupService = new ProductGroupService();
            productImportService = new ProductImportService();
            productService = new ProductService();
            
            userService = new UserService();
            
            District district = new District("Petrovsky");
            SocialStatus socialStatus = new SocialStatus("Bomj");
            
            Company company = new Company();
            company.setName("IBM");
            
            List<Company> companies = new LinkedList<Company>();
            companies.add(company);
            
            List<Committent> committents = new LinkedList<Committent>();
            
            Committent committent = new Committent();
            
            committent.setName("Vasya");
            committent.setSurname("Pupkin");
            committent.setPatronymic("Antonovich");
            committent.setDistrict(district);
            committent.setSocialStatus(socialStatus);
            committent.setDate(new Date());
            committent.setTelephoneNumber("+380502728020");
            committent.setCompanies(companies);
            
            committents.add(committent);
            
            User user = new User("admin", DigestUtils.sha256Hex("1111" + "admin"));
            user.setStatus(UserStatus.Admin);
            
            userService.create(user);
            
            districtService.create(district);
            socialStatusService.create(socialStatus);
            companyService.create(company);
            committentService.create(committent);
            
            ProductGroup productGroup = new ProductGroup("Sport product");
            productGroupService.create(productGroup);
            
            Product product = new Product(productGroup, "Ball", new BigDecimal(1000));
            productService.create(product);
            
            ProductImport productImport = new ProductImport(product, 1, new Date(), 14);
            productImportService.create(productImport);
            
            Deal deal = new Deal(product, new Date(), 4);
            dealService.create(deal);
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            try
            {
                print();
            } catch (RemoteException e)
            {
                e.printStackTrace();
            }
                        
            companyService.disconnect();
            districtService.disconnect();
            socialStatusService.disconnect();
            committentService.disconnect();
            
            dealService.disconnect();
            productGroupService.disconnect();
            productImportService.disconnect();
            productGroupService.disconnect();
        }         
    }
    
    private void print() throws RemoteException
    {
        area.appendText("Start" + System.lineSeparator());
        
        for (Committent c : committentService.findAll())
        {
            area.appendText(c.getName() + System.lineSeparator());
            area.appendText(c.getSurname() + System.lineSeparator());
            area.appendText(c.getCompanies().iterator().next().getName() + System.lineSeparator());
        }
        
        for (Deal d : dealService.findAll())
        {
            area.appendText(d.getProduct().getName() + System.lineSeparator());
            area.appendText(d.getDate().toString() + System.lineSeparator());
            area.appendText(d.getCount() + System.lineSeparator());
        }
        
        if (DigestUtils.sha256Hex("1111" + "admin").equals(userService.find("admin").getPassword()))
            area.appendText("True");
    }
}
