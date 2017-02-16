/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class MyRequestDecoder extends ChannelInboundHandlerAdapter  {

    
    public MyRequestDecoder() {
       
    }
 

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.debug("In http request decoder");
        ctx.fireChannelRead(msg);
    }


  

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }

   
}
