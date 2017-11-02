package core;
import command.CreateCommand;
import dao.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import servise.Service;
import servise.UserService;

public class CreateCommandHandler 
extends SimpleChannelInboundHandler<CreateCommand>
{    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateCommand msg) throws Exception
    {
        if (msg instanceof CreateCommand)
        {
            System.out.println("Cathed");
            System.out.println(msg.entry.getClass());
            
            if (msg.entry instanceof User)
            {
                System.out.println("Catched user");
                
                Service<User> service = new UserService();
                
                service.create((User) msg.entry);
                /*
                User user = (User) msg.entry;
                                
                System.out.println(user.getLogin());
                System.out.println(user.getPassword());
                */
            }
        }
    }    
}
