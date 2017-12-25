package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dao.Deal;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public class DealTableColumnBuilder 
implements TableColumnBuilder<Deal>
{
    @Override
    public List<TableColumn<Deal, String>> buildTableColumns()
    {
        List<TableColumn<Deal, String>> dealColumnList = new ArrayList<>();
        
        Field declaredFields[] = Deal.class.getDeclaredFields();
        
        for (int i = 1; i < declaredFields.length; i++)
            dealColumnList.add(new TableColumn<Deal, String>(declaredFields[i].getName()));
        
        dealColumnList.get(0).setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        
        dealColumnList.get(1).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        
        dealColumnList.get(2).setCellValueFactory(
                cellData -> new SimpleStringProperty("[" + String.valueOf(cellData.getValue().getCommittent().getId()) + "] " + 
                        cellData.getValue().getCommittent().getSurname()));
        
        dealColumnList.get(3).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        
        dealColumnList.get(4).setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCount())));
        
        dealColumnList.get(5).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getPrice().toString()));
        
        return dealColumnList;
    }  
}
