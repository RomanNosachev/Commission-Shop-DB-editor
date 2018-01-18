package utils.comboBoxConverter;

import dao.ProductGroup;
import javafx.util.StringConverter;

public class ProductGroupConverter 
extends StringConverter<ProductGroup>
{
    @Override
    public String toString(ProductGroup object)
    {
        return object.getName();
    }
    
    @Override
    public ProductGroup fromString(String string)
    {
        return new ProductGroup(string);
    }
}
