package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dao.SocialStatus;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public class SocialStatusTableColumnBuilder 
implements TableColumnBuilder<SocialStatus>
{
    @Override
    public List<TableColumn<SocialStatus, String>> buildTableColumns()
    {
        List<TableColumn<SocialStatus, String>> socialStatusColumnList = new ArrayList<>();
        
        Field declaredFields[] = SocialStatus.class.getDeclaredFields();
        
        for (int i = 1; i < declaredFields.length; i++)
            socialStatusColumnList.add(new TableColumn<SocialStatus, String>(declaredFields[i].getName()));
        
        socialStatusColumnList.get(0).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        
        return socialStatusColumnList;
    }   
}
