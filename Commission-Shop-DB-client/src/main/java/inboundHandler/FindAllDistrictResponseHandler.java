package inboundHandler;

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
    }
}
