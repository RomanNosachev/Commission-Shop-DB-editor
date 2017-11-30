package inboundHandler;

import dao.Committent;
import io.netty.channel.ChannelHandlerContext;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import response.FindAllResponse;

public class FindAllCommittentResponceHandler 
extends AbstractFindAllResponceHandler<Committent>
{
    public FindAllCommittentResponceHandler(TableView<Committent> tableView)
    {
        super(Committent.class, tableView);
    }

    @Override
    public void responceReceived(ChannelHandlerContext ctx, FindAllResponse<Committent> msg)
    {
        tableView.setItems(FXCollections.observableArrayList(msg.getEntries()));
    }
}
