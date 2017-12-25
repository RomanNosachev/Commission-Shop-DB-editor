package responce;

import command.Command;
import dao.UserStatus;

public class LoginResponce 
implements Command
{
    private static final long serialVersionUID = -7023688538646915090L;
    
    private UserStatus  status = UserStatus.User;
    private boolean     logged;
    
    public LoginResponce(boolean logged)
    {
        this.logged = logged;
    }
    
    public LoginResponce(boolean logged, UserStatus status)
    {
        this(logged);
        
        this.status = status;
    }
    
    public boolean isLogged()
    {
        return logged;
    }
    
    public UserStatus getStatus()
    {
        return status;
    }
}
