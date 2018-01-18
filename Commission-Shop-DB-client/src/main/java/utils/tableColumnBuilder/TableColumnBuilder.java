package utils.tableColumnBuilder;

import java.util.List;

import dao.DB_Entity;
import javafx.scene.control.TableColumn;

public interface TableColumnBuilder<T extends DB_Entity>
{
    public abstract List<TableColumn<T, String>> buildTableColumns();
}
