package controller;

import java.util.List;

import dao.DB_Directory;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class DirectoryController<T extends DB_Directory>
extends EntityController<T>
{
    private ComboBox<T> comboBox;
    
    public DirectoryController(Class<T> type, TableView<T> tableView, TextField sheetField)
    {
        this(type, tableView, sheetField, new ComboBox<T>());
    }
    
    public DirectoryController(Class<T> type, TableView<T> tableView, TextField sheetField, ComboBox<T> comboBox)
    {
        super(type, tableView, sheetField);
        
        this.comboBox = comboBox;
    }
    
    @Override
    public void setItems(List<T> items, int sheet)
    {
        tableView.setItems(FXCollections.observableArrayList(items));        
        
        //TODO
        System.out.println("set");
        
        comboBox.getItems().setAll(items);
    }
}
