package utils.comboBoxConverter;

import dao.SocialStatus;
import javafx.util.StringConverter;

public class SocialStatusConverter 
extends StringConverter<SocialStatus>
{
    @Override
    public String toString(SocialStatus object)
    {
        return object.getName();
    }
    
    @Override
    public SocialStatus fromString(String name)
    {
        return new SocialStatus(name);
    }
}
