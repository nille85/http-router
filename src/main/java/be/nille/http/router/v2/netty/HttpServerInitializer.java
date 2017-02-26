/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.netty;


import be.nille.http.router.netty.RequestDecodingValidator;
import be.nille.http.router.netty.RequestTransformer;
import be.nille.http.router.v2.route.Router;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;


/**
 *
 * @author nholvoet
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private final Router router;

    public HttpServerInitializer(final Router router) {
        this.router = router;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

       
        p.addLast("decoder", new HttpRequestDecoder());
        // Uncomment the following line if you don't want to handle HttpChunks. 
        //pipeline.addLast("aggregator", new HttpChunkAggregator(1048576)); 
        p.addLast("encoder", new HttpResponseEncoder());
        // Remove the following line if you don't want automatic content compression. 
        p.addLast("deflater", new HttpContentCompressor());
        p.addLast(new RequestDecodingValidator());
        p.addLast(new RequestTransformer());
      
        p.addLast(new RequestHandler(router));
    
        p.addLast(new ResponseWriter());
    }
}
