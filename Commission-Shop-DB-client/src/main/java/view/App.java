package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        
        Scene scene = new Scene(p);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Comission Shop DB-client");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        
        primaryStage.show();
    }

	public static void main(String[] args) 
	{	    
		launch(args);
	}
}
