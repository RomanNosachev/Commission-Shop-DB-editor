package dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

@NamedEntityGraph(name = "Company", includeAllAttributes = true)

@Entity
@Table(name = "Company")
public class Company 
implements DB_Entity
{            
    private static final long serialVersionUID = 3854696488171722397L;
    
    @Id
    @Column(name = "name", length = 40, unique = true, nullable = false)
    private String name;
    
    public Company()
    {
        
    }
    
    public Company(String name)
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
