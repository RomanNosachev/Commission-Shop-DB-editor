package dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ProductGroup")
public class ProductGroup 
implements Serializable
{        
    private static final long serialVersionUID = -2552842837729247867L;
    
    @Id
    @Column(name = "Name", length = 40, unique = true, nullable = false)
    private String name;
    
    public ProductGroup()
    {
    }
    
    public ProductGroup(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}