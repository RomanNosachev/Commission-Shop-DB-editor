package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dao.Committent;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public class CommittentTableColumnBuilder 
implements TableColumnBuilder<Committent>
{
    @Override
    public List<TableColumn<Committent, String>> buildTableColumns()
    {
        List<TableColumn<Committent, String>> committentColumnList = new ArrayList<>();
        
        Field declaredFields[] = Committent.class.getDeclaredFields();
        
        for (int i = 1; i < declaredFields.length; i++)
            committentColumnList.add(new TableColumn<Committent, String>(declaredFields[i].getName()));

        committentColumnList.get(0).setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        committentColumnList.get(1).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        committentColumnList.get(2).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        committentColumnList.get(3).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getPatronymic()));
        committentColumnList.get(4).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getDistrict().getName()));
        committentColumnList.get(5).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getSocialStatus().getName()));
        committentColumnList.get(6).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getCompaniesString()));
        committentColumnList.get(7).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        committentColumnList.get(8).setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getTelephoneNumber()));
        
        return committentColumnList;
    }
}
