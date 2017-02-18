/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import be.nille.http.router.response.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class ResponseInterceptor extends ChannelInboundHandlerAdapter  {



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
     
         if(msg instanceof TCPConnection){
             ctx.fireChannelRead(msg);
         }
        
         if (msg instanceof Response) {
             Response response = (Response) msg;
             //do something with response
             ctx.fireChannelRead(response);
            
         }   
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }

   
}
