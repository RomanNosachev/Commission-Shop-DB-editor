package controller;

import java.util.ArrayList;
import java.util.List;

import dao.DB_Directory;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class DirectoryController<T extends DB_Directory>
extends EntityController<T>
{
    private List<ComboBox<T>> comboBoxes = new ArrayList<ComboBox<T>>();

    public DirectoryController(Class<T> type, TableView<T> tableView, TextField sheetField, List<ComboBox<T>> comboBoxes)
    {
        super(type, tableView, sheetField);
        
        this.comboBoxes = comboBoxes;
    }
    
    @Override
    public void setItems(List<T> items, int sheet)
    {
        tableView.getItems().setAll(items);       
        
        for (int i = 0; i < comboBoxes.size(); i++)
            comboBoxes.get(i).getItems().setAll(items);
    }
}
