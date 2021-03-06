package dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

@NamedEntityGraph(name = "SocialStatus", includeAllAttributes = true)

@Entity
@Table(name = "SocialStatus")
public class SocialStatus 
implements DB_Entity
{                    
    private static final long serialVersionUID = -8371093617546670432L;
    
    @Id
    @Column(name = "name", length = 20, unique = true, nullable = false)
    private String name;

    public SocialStatus()
    {
        
    }
    
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
