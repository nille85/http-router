/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import be.nille.http.router.request.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class RequestTransformer extends ChannelInboundHandlerAdapter  {

    
 
    private HttpRequest httpRequest;
    private final StringBuffer contentBuffer;
    
    public RequestTransformer(){
        contentBuffer = new StringBuffer();
    }
 

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
     
        
         if (msg instanceof HttpRequest) {
            log.info("HTTP Request received through channel");
            this.httpRequest = (HttpRequest) msg;
            Charset charset = HttpUtil.getCharset(httpRequest);
            
            HttpMetaData metaData = new HttpMetaData(HttpUtil.isKeepAlive(httpRequest),  charset);
            ctx.fireChannelRead(metaData);
            log.info("Content: " + msg.toString());
            
         }
         if (msg instanceof HttpContent) {
            log.info("HTTP Content received through channel");
            HttpContent httpContent = (HttpContent) msg;
            contentBuffer.append(httpContent.content().toString(CharsetUtil.UTF_8));
            
            if (msg instanceof LastHttpContent) {
                log.info("Last Http Content received through channel");
                log.info("Print the whole body");
                log.info("Content: " + contentBuffer.toString());
                
                Request request = new NettyRequest(httpRequest, contentBuffer.toString() );
                ctx.fireChannelRead(request);
                 
            }
         }
         
        
                
         
         
       
        
        
        
        
        
    }


  

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }

   
}
