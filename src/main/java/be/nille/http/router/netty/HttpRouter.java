package be.nille.http.router.netty;


import be.nille.http.router.route.Router;
import be.nille.http.router.route.ListRoute;
import be.nille.http.router.route.Route;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpRouter implements Router {

   
    private final Port port;
    private final Route route;
    
    public HttpRouter(final Route route) {
        this(route,new Port(80));
    }
    
    public HttpRouter(final Route route, final int port) {
        this(route,new Port(port));
    }
    
    public HttpRouter(final Route route, final Port port) {
        this.route = route;
        this.port = port;
    }

    
    @Override
    public void start() throws Exception {
        
        //boss group accepts the new connections
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // Configure the server. 
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInitializer(route))
                    .localAddress(new InetSocketAddress(port.getValue()));

            Channel ch = b.bind().sync().channel();

            ch.closeFuture().sync();
        } finally {
            log.info("Closing down netty threads");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

   

}
