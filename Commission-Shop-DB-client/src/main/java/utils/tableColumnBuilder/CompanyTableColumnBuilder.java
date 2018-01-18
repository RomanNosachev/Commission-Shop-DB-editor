package utils.tableColumnBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dao.Company;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public class CompanyTableColumnBuilder 
implements TableColumnBuilder<Company>
{
    @Override
    public List<TableColumn<Company, String>> buildTableColumns()
    {
        List<TableColumn<Company, String>> companyColumnList = new ArrayList<>();
        
        Field declaredFields[] = Company.class.getDeclaredFields();
        
        for (int i = 1; i < declaredFields.length; i++)
            companyColumnList.add(new TableColumn<Company, String>(declaredFields[i].getName()));
        
        companyColumnList.get(0).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        
        return companyColumnList;
    }    
}
