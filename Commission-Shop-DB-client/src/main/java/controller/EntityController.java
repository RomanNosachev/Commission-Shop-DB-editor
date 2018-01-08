package controller;

import java.util.List;

import dao.DB_Entity;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import utils.TableColumnBuilder;

public class EntityController<T extends DB_Entity>
{
    private Class<T> type;
    
    protected TableView<T>    tableView;
    private TextField       sheetField;
    
    private int sheet = 1;
    
    public EntityController(Class<T> type, TableView<T> tableView, TextField sheetField)
    {
        this.type = type;
        this.tableView = tableView;
        this.sheetField = sheetField;
    }
    
    public EntityController<T> columnBuilder(TableColumnBuilder<T> tableColumnBuilder)
    {
        tableView.getColumns().addAll(tableColumnBuilder.buildTableColumns());
        
        return this;
    }
    
    public Class<T> getEntityClass()
    {
        return type;
    }
    
    public void setTableView(TableView<T> tableView)
    {
        this.tableView = tableView;
    }
    
    public TableView<T> getTableView()
    {
        return tableView;
    }

    public void setSheet(int sheet)
    {
        this.sheet = sheet;
        
        sheetField.setText(String.valueOf(sheet));
    }

    public int getSheet()
    {
        return sheet;
    }
    
    public void setItems(List<T> items, int sheet)
    {
        tableView.setItems(FXCollections.observableArrayList(items));
        setSheet(sheet);
    }
}
