package dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

@NamedEntityGraph(name = "ProductGroup", includeAllAttributes = true)

@Entity
@Table(name = "ProductGroup")
public class ProductGroup 
implements DB_Directory
{        
    private static final long serialVersionUID = -2552842837729247867L;
    
    @Id
    @Column(name = "name", length = 40, unique = true, nullable = false)
    private String name;
    
    public ProductGroup()
    {
    }
    
    public ProductGroup(String name)
    {
        super();
        
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