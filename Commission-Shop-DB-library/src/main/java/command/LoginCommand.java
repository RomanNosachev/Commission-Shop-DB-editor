package command;

import dao.User;

public class LoginCommand
extends AbstractEntityCommand<User>
{
    private static final long serialVersionUID = 932749875590158691L;
    
    private User user;
    
    public LoginCommand(User user)
    {
        super(User.class);
        
        if (user == null || user.getLogin() == null || user.getPassword() == null)
            throw new NullPointerException();
        
        this.user = user;
        
        System.out.println(user.getPassword());
        
        //user.setPassword(DigestService.getMD5(user.getPassword()));
        
        System.out.println(user.getPassword());
    }
    
    public User getUser()
    {
        //user.setPassword(DigestUtils.getMd5Digest().toString());
        
        System.out.println(user.getPassword());
        
        return user;
    }
}
