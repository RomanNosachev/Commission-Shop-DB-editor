package command;

public class LoginResponceCommand 
implements Command
{
    private static final long serialVersionUID = -7023688538646915090L;
    
    private boolean login;
    
    public LoginResponceCommand(boolean login)
    {
        this.login = login;
    }
    
    public boolean isLogged()
    {
        return login;
    }
}
