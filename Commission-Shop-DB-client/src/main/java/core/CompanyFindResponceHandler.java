package core;

import dao.Company;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CompanyFindResponceHandler 
extends SimpleChannelInboundHandler<Company>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Company msg) throws Exception
    {
        System.out.println("Catched");
        System.out.println(msg.getName());
    }
}
