package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dao.ProductImport;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public class ProductImportTableColumnBuilder 
implements TableColumnBuilder<ProductImport>
{
    @Override
    public List<TableColumn<ProductImport, String>> buildTableColumns()
    {
        List<TableColumn<ProductImport, String>> productImportColumnList = new ArrayList<>();
        
        Field declaredFields[] = ProductImport.class.getDeclaredFields();
        
        for (int i = 1; i < declaredFields.length; i++)
            productImportColumnList.add(new TableColumn<ProductImport, String>(declaredFields[i].getName()));
        
        productImportColumnList.get(0).setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        
        productImportColumnList.get(1).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        
        productImportColumnList.get(2).setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getReceiptNumber())));
        
        productImportColumnList.get(3).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        
        productImportColumnList.get(4).setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCount())));
        
        return productImportColumnList;
    }   
}
