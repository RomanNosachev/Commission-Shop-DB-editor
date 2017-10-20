package view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import dao.Committent;
import dao.Company;
import dao.Deal;
import dao.District;
import dao.Product;
import dao.ProductGroup;
import dao.ProductImport;
import dao.SocialStatus;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import servise.CommittentServise;
import servise.CompanyServise;
import servise.DealService;
import servise.DistrictService;
import servise.ProductGroupService;
import servise.ProductImportService;
import servise.ProductService;
import servise.Service;
import servise.SocialStatusServise;

public class MainForm 
extends Application 
{
    @FXML
    private Stage      primaryStage;
    @FXML
    private BorderPane rootLayout;
    @FXML
    private TextArea   area;
        
    private Service<Company> companyServise;
    private Service<Committent> committentServise;
    private Service<District> districtService;
    private Service<SocialStatus> socialStatusServise;
    
    private Service<Deal> dealService;
    private Service<ProductGroup> productGroupService;
    private Service<ProductImport> productImportService;
    private Service<Product> productService;
    
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
        
        //listen();
    }
    
    public void main()
    {
        launch();
    }
    
    public void listen()
    {
        ServerSocket serverSocket = null;
        Socket localSocket = null;
        
        DataInputStream in = null;
        DataOutputStream out = null;
        
        try
        {
            serverSocket = new ServerSocket(2070);
            
            localSocket = new Socket("localhost", 2070);
            
            in = new DataInputStream(localSocket.getInputStream());
            out = new DataOutputStream(localSocket.getOutputStream());
            
            if (serverSocket.accept() != null)
            {
                String message = in.readUTF();
                
                System.out.println("Socket:" + message);
            }
        } catch (IOException e1)
        {
            e1.printStackTrace();
        }
        finally 
        {
            try
            {
                serverSocket.close();
                localSocket.close();
                
                in.close();
                out.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void test()
    { 
        SessionFactory factory = null;
        
        try 
        {
            factory = new Configuration().configure().buildSessionFactory();
            
            companyServise = new CompanyServise(factory);
            committentServise = new CommittentServise(factory);
            districtService = new DistrictService(factory);
            socialStatusServise = new SocialStatusServise(factory);
            
            dealService = new DealService(factory);
            productGroupService = new ProductGroupService(factory);
            productImportService = new ProductImportService(factory);
            productService = new ProductService(factory);
            
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
            
            districtService.create(district);
            socialStatusServise.create(socialStatus);
            companyServise.create(company);
            committentServise.create(committent);
            
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
            print();
            
            factory.close();
            
            companyServise.disconnect();
            districtService.disconnect();
            socialStatusServise.disconnect();
            committentServise.disconnect();
            
            dealService.disconnect();
            productGroupService.disconnect();
            productImportService.disconnect();
            productGroupService.disconnect();
        } 
    }
    
    private void print()
    {
        area.appendText("Start" + System.lineSeparator());
        
        for (Committent c : committentServise.findAll())
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
    }
}
