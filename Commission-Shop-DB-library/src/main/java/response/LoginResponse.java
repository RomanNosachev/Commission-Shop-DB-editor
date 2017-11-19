package response;

import command.Command;

public class LoginResponse 
implements Command
{
    private static final long serialVersionUID = -7023688538646915090L;
    
    private boolean login;
    
    public LoginResponse(boolean login)
    {
        this.login = login;
    }
    
    public boolean isLogged()
    {
        return login;
    }
}
