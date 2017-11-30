package inboundHandler;

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
    }

    @Override
    public void responceReceived(ChannelHandlerContext ctx, FindAllResponse<Company> msg)
    {
        //TODO
    }
}
