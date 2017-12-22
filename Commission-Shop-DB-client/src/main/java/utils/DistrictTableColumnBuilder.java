package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dao.District;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public class DistrictTableColumnBuilder 
implements TableColumnBuilder<District>
{
    @Override
    public List<TableColumn<District, String>> buildTableColumns()
    {
        List<TableColumn<District, String>> districtColumnList = new ArrayList<>();
        
        Field declaredFields[] = District.class.getDeclaredFields();
        
        for (int i = 1; i < declaredFields.length; i++)
            districtColumnList.add(new TableColumn<District, String>(declaredFields[i].getName()));
        
        districtColumnList.get(0).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        
        return districtColumnList;
    } 
}
