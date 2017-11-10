package command;

import dao.User;
import util.DigestService;

public class LoginCommand
extends AbstractEntityCommand<User>
{
    private static final long serialVersionUID = 932749875590158691L;
    
    private User user;
    
    public LoginCommand(User user)
    {
        super(User.class);
        this.user = user;
        user.setPassword(DigestService.getPasswordHash(user));
    }
    
    public User getUser()
    {
        return user;
    }
}
