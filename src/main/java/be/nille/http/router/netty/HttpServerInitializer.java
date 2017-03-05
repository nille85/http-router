/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;




import be.nille.http.router.route.Route;
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

    private final Route route;

    public HttpServerInitializer(final Route route) {
        this.route = route;
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
        p.addLast(new RequestHandler(route));
        p.addLast(new ResponseWriter());
    }
}
