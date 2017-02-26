package be.nille.http.router.v2.netty;


import be.nille.http.router.v2.route.Router;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyHttpServer implements HttpServer {

   
    private final int port;
    private final Router router;
    
    public NettyHttpServer(final Router router, final int port) {
        this.router = router;
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
                    .childHandler(new HttpServerInitializer(router))
                    .localAddress(new InetSocketAddress(port));

            Channel ch = b.bind().sync().channel();

            ch.closeFuture().sync();
        } finally {
            log.info("Closing down netty threads");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

   

}
