package inboundHandler;

<<<<<<< HEAD
import dao.District;
import io.netty.channel.ChannelHandlerContext;
import javafx.scene.control.TableView;
import response.FindAllResponse;

public class FindAllDistrictResponseHandler 
extends AbstractFindAllResponceHandler<District>
{
    public FindAllDistrictResponseHandler(TableView<District> tableView)
    {
        super(District.class, tableView);
    }

    @Override
    public void responceReceived(ChannelHandlerContext ctx, FindAllResponse<District> msg)
    {
        // TODO Auto-generated method stub
=======
import controller.ClientController;
import dao.District;
import io.netty.channel.ChannelHandlerContext;
import response.FindAllResponse;

public class FindAllDistrictResponseHandler 
extends AbstractResponseHandler<District>
{
    public FindAllDistrictResponseHandler(ClientController controller)
    {
        super(District.class, controller);
    }

    @Override
    public void responceReceived(ChannelHandlerContext ctx, FindAllResponse<District> msg)
    {
        //TODO
>>>>>>> refs/remotes/origin/dev
    }
}
