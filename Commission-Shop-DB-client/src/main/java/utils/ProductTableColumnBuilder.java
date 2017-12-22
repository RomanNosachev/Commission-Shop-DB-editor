package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dao.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public class ProductTableColumnBuilder 
implements TableColumnBuilder<Product>
{
    @Override
    public List<TableColumn<Product, String>> buildTableColumns()
    {
        List<TableColumn<Product, String>> productColumnList = new ArrayList<>();
        
        Field declaredFields[] = Product.class.getDeclaredFields();
        
        for (int i = 1; i < declaredFields.length; i++)
            productColumnList.add(new TableColumn<Product, String>(declaredFields[i].getName()));
        
        productColumnList.get(0).setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        
        productColumnList.get(1).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getProductGroup().getName()));
        
        productColumnList.get(2).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        
        productColumnList.get(3).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getPrice().toString()));
        
        return productColumnList;
    }    
}
