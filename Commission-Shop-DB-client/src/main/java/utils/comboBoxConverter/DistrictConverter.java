package utils.comboBoxConverter;

import dao.District;
import javafx.util.StringConverter;

public class DistrictConverter 
extends StringConverter<District>
{
    @Override
    public String toString(District object)
    {
        return object.getName();
    }
    
    @Override
    public District fromString(String name)
    {
        return new District(name);
    }
}
