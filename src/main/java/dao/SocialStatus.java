package dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SocialStatus")
public class SocialStatus 
implements Serializable
{                
    private static final long serialVersionUID = 74324605955615390L;
    
    @Id
    @Column(name = "Name", length = 20, unique = true, nullable = false)
    private String name;

    public SocialStatus(String name)
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
