package command;

import dao.User;

public class LoginCommand
extends AbstractCommand<User>
{
    private static final long serialVersionUID = 932749875590158691L;
    
    private User user;
    
    public LoginCommand(User user)
    {
        super(User.class);
        this.user = user;
    }
    
    public User getUser()
    {
        return user;
    }
}
