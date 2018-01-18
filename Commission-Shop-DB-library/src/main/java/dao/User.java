package dao;

import java.io.Serializable;

public class User 
implements Serializable
{
    private static final long serialVersionUID = -235737822197145908L;

    private String login;    
    private String password;

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
