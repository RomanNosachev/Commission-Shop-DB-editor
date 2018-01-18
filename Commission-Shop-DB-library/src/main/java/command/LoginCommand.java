package command;

import java.io.Serializable;

import org.apache.commons.codec.digest.DigestUtils;

import dao.User;

public class LoginCommand
implements Serializable
{
    private static final long serialVersionUID = 932749875590158691L;
    
    private User user;
    
    public LoginCommand(User user)
    {        
        if (user == null || user.getLogin() == null || user.getPassword() == null)
            throw new NullPointerException();
        
        this.user = user;
        user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
    }
    
    public User getUser()
    {                
        return user;
    }
}
