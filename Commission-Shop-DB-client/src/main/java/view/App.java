package view;

import java.io.IOException;

import controller.ClientController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App 
extends Application 
{        
    @Override
    public void start(Stage primaryStage)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/MainForm.fxml"));
        
        Parent p = null;
        
        try
        {
            p = fxmlLoader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        final ClientController controller = fxmlLoader.getController();
        
        Scene scene = new Scene(p);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Comission Shop DB-client");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() 
        {
            public void handle(WindowEvent event)
            {
                controller.shutdown();
                
                Platform.exit();
            }
        });
        
        primaryStage.show();        
    }

	public static void main(String[] args) 
	{	    
		launch(args);
	}
}
