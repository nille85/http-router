package be.nille.http;

import be.nille.http.route.Path;
import be.nille.http.route.RequestMethod;
import be.nille.http.route.Route;
import be.nille.http.route.RouteRegistry;
import be.nille.http.route.impl.SimpleHandler;
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
public class HttpServer {

    static final boolean SSL = System.getProperty("ssl") != null;

    private final int port;
    private final RouteRegistry registry;

    public HttpServer(int port, final RouteRegistry registry) {
        this.port = port;
        this.registry = registry;
    }

    public void run() throws Exception {
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
                    .childHandler(new HttpServerInitializer(sslCtx, registry))
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

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }

        RouteRegistry registry = new RouteRegistry()
                .withRoute(new Route(
                                new Path("/subscriptions/{subscriptionId}"), new RequestMethod("GET"), new SimpleHandler())
                )
                .withRoute(new Route(
                                new Path("/subscriptions"), new RequestMethod("POST"), new SimpleHandler())
                );
        
        new HttpServer(port, registry).run();
    }
}
