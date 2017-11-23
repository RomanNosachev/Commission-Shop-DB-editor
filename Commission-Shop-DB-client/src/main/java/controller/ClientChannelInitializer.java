package controller;

<<<<<<< HEAD
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
=======
import inboundHandler.ClientDefaultHandler;
import inboundHandler.FindAllCompanyResponseHandler;
import inboundHandler.FindAllDistrictResponseHandler;
import inboundHandler.FindAllResponseHandler;
import inboundHandler.LoginResponseHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import outboundHandler.ClientHandler;

public class ClientChannelInitializer 
extends ChannelInitializer<SocketChannel>
{
    private final ClientController controller;
    
    public ClientChannelInitializer(ClientController controller)
    {
        this.controller = controller;
    }
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
        
        pipeline.addLast(new ClientHandler());
        
        pipeline.addLast(new LoginResponseHandler());
        pipeline.addLast(new FindAllCompanyResponseHandler(controller));
        pipeline.addLast(new FindAllDistrictResponseHandler(controller));
        pipeline.addLast(new FindAllResponseHandler(controller));
        pipeline.addLast(new ClientDefaultHandler());
>>>>>>> refs/remotes/origin/dev
    }
}
