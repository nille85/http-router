/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.netty;






import be.nille.http.router.media.TextMedia;

import be.nille.http.router.v2.request.Request;
import be.nille.http.router.v2.response.Response;
import be.nille.http.router.v2.response.RouterResponse;
import be.nille.http.router.v2.response.StatusCode;
import be.nille.http.router.v2.response.StatusCodeException;
import be.nille.http.router.v2.route.Router;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class RequestHandler extends ChannelInboundHandlerAdapter {

    private final Router router;

    public RequestHandler(final Router router){
        this.router = router;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        
        if (msg instanceof Request) {
            Response response;
            Request request = (Request) msg;
            try {
                response = router.response(request);
            } catch (StatusCodeException sce) {
                response = new RouterResponse(new StatusCode(400), new TextMedia(""), new HashMap<>());
                
            } catch (Exception ex) {
                 response = new RouterResponse(new StatusCode(500), new TextMedia(""), new HashMap<>());
            }
            ctx.fireChannelRead(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }

}
