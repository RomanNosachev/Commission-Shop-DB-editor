package dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "District")
public class District 
implements Serializable
{                    
    private static final long serialVersionUID = -5349742291113244498L;
    
    @Id
    @Column(name = "name", length = 30, unique = true, nullable = false)
    private String name;
    
    public District()
    {
        
    }
    
    public District(String name)
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
