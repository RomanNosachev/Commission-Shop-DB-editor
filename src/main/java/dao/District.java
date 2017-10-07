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
    private static final long serialVersionUID = 4543928301197217785L;
    
    @Id
    @Column(name = "Name", length = 30, unique = true, nullable = false)
    private String name;

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
