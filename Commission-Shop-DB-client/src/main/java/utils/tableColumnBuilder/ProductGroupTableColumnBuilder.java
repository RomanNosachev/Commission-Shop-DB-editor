package utils.tableColumnBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dao.ProductGroup;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public class ProductGroupTableColumnBuilder 
implements TableColumnBuilder<ProductGroup>
{
    @Override
    public List<TableColumn<ProductGroup, String>> buildTableColumns()
    {
        List<TableColumn<ProductGroup, String>> productGroupColumnList = new ArrayList<>();
        
        Field declaredFields[] = ProductGroup.class.getDeclaredFields();
        
        for (int i = 1; i < declaredFields.length; i++)
            productGroupColumnList.add(new TableColumn<ProductGroup, String>(declaredFields[i].getName()));
        
        productGroupColumnList.get(0).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        
        return productGroupColumnList;
    }  
}
