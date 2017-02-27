/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;






import be.nille.http.router.body.TextBody;
import be.nille.http.router.headers.Headers;

import be.nille.http.router.request.Request;
import be.nille.http.router.response.Response;
import be.nille.http.router.response.RouteResponse;
import be.nille.http.router.response.StatusCode;
import be.nille.http.router.route.Route;
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

    private final Route route;

    public RequestHandler(final Route route){
        this.route = route;
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
                response = route.response(request);
                if(response.isEmpty()){
                    response = new RouteResponse(new StatusCode(404), new TextBody(""), new Headers());
                }
            } catch (Exception ex) {
                 response = new RouteResponse(new StatusCode(500), new TextBody(""), new Headers());
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
