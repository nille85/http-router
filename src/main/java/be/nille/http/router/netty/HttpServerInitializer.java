/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import be.nille.http.router.exception.ExceptionHandler;
import be.nille.http.router.RouteRegistry;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;

/**
 *
 * @author nholvoet
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;
    private final RouteRegistry registry;
    private final ExceptionHandler exceptionHandler;

    public HttpServerInitializer(SslContext sslCtx, final RouteRegistry registry, final ExceptionHandler exceptionHandler) {
        this.sslCtx = sslCtx;
        this.registry = registry;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }

        p.addLast("decoder", new HttpRequestDecoder());
        // Uncomment the following line if you don't want to handle HttpChunks. 
        //pipeline.addLast("aggregator", new HttpChunkAggregator(1048576)); 
        p.addLast("encoder", new HttpResponseEncoder());
        // Remove the following line if you don't want automatic content compression. 
        p.addLast("deflater", new HttpContentCompressor());
        p.addLast(new RequestDecodingValidator());
        p.addLast(new RequestTransformer());
        p.addLast(new RequestInterceptor());
        p.addLast(new RequestHandler(registry, exceptionHandler));
        p.addLast(new ResponseInterceptor());
        p.addLast(new ResponseWriter());
    }
}
