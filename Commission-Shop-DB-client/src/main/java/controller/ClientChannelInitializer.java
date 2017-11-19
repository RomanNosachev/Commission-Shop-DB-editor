package controller;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ClientChannelInitializer 
extends ChannelInitializer<SocketChannel>
{
    private final ClientController  clientController;
    private AbstractNetworkController   networkController;
    
    public ClientChannelInitializer(ClientController clientController)
    {
        this.clientController = clientController;
    }
    
    public ChannelInitializer<SocketChannel> setNetworkController(AbstractNetworkController networkController)
    {
        this.networkController = networkController;
        
        return this;
    }
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        /*
        ChannelPipeline pipeline = ch.pipeline();
        
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
        
        pipeline.addLast(new ClientHandler());
        
        pipeline.addLast(new LoginResponseHandler(networkController));
        pipeline.addLast(new FindAllCompanyResponseHandler(clientController));
        pipeline.addLast(new FindAllDistrictResponseHandler(clientController));
        pipeline.addLast(new ClientDefaultHandler());
        */
    }
}
