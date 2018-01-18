package utils.comboBoxConverter;

import dao.Company;
import javafx.util.StringConverter;

public class CompanyConverter 
extends StringConverter<Company>
{
    @Override
    public String toString(Company object)
    {
        return object.getName();
    }
    
    @Override
    public Company fromString(String name)
    {
        return new Company(name);
    }
}
