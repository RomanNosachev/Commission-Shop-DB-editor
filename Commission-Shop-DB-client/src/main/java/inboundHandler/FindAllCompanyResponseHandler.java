package inboundHandler;

<<<<<<< HEAD
import dao.Company;
import io.netty.channel.ChannelHandlerContext;
import javafx.scene.control.TableView;
import response.FindAllResponse;

public class FindAllCompanyResponseHandler 
extends AbstractFindAllResponceHandler<Company>
{
    public FindAllCompanyResponseHandler(TableView<Company> tableView)
    {
        super(Company.class, tableView);
=======
import controller.ClientController;
import dao.Company;
import io.netty.channel.ChannelHandlerContext;
import response.FindAllResponse;

public class FindAllCompanyResponseHandler 
extends AbstractResponseHandler<Company>
{
    public FindAllCompanyResponseHandler(ClientController controller)
    {        
        super(Company.class, controller);
>>>>>>> refs/remotes/origin/dev
    }

    @Override
    public void responceReceived(ChannelHandlerContext ctx, FindAllResponse<Company> msg)
    {
        //TODO
    }
}
