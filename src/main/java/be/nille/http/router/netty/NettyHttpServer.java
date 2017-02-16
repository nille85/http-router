package be.nille.http.router.netty;

import be.nille.http.route.exception.ExceptionHandler;
import be.nille.http.router.RouteRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyHttpServer implements HttpServer {

    static final boolean SSL = System.getProperty("ssl") != null;

    private final int port;
    private final ExceptionHandler exceptionHandler;
    

    public NettyHttpServer(final int port, final ExceptionHandler exceptionHandler) {
        this.port = port;
        this.exceptionHandler = exceptionHandler;
        
        
    }

    @Override
    public void run(final RouteRegistry registry) throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        // Configure the server. 
        ServerBootstrap b = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInitializer(sslCtx, registry, exceptionHandler))
                    .localAddress(new InetSocketAddress(port));

            Channel ch = b.bind().sync().channel();

            log.info("Server is available at  "
                    + (SSL ? "https" : "http") + "://127.0.0.1:" + port + '/');

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    
}
