package dao;

import java.io.Serializable;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Table(name = "Company")
public class Company 
implements Serializable
{        
    private static final long serialVersionUID = -6081702849884309255L;
    
    @Id
    @Column(name = "Name", length = 40, unique = true, nullable = false)
    private String name;
    
    public Company(String name)
    {
        super();
        this.name = name;
    }

    public Company()
    {
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
