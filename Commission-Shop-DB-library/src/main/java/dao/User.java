package dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

@NamedEntityGraph(name = "User", includeAllAttributes = true)

@Entity
@Table(name = "_User")
public class User 
implements DB_Entity
{
    private static final long serialVersionUID = -235737822197145908L;

    @Id
    @Column(name = "Login", unique = true, nullable = false, length = 30)
    private String login;
    
    @Column(name = "Password", nullable = false, length = 64)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "UserStatus", nullable = false)
    private UserStatus status;
    
    public User()
    {
        status = UserStatus.User;
    }

    public User(String login, String password)
    {
        super();
        this.login = login;
        this.password = password;
        status = UserStatus.User;
    }
    
    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public UserStatus getStatus()
    {
        return status;
    }

    public void setStatus(UserStatus status)
    {
        this.status = status;
    }
}
