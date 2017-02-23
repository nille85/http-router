/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import be.nille.http.router.exception.ExceptionHandler;
import be.nille.http.router.HttpRouterException;
import be.nille.http.router.RouteRegistry;

import be.nille.http.router.request.Request;
import be.nille.http.router.response.Response;
import be.nille.http.router.response.StatusCode;
import be.nille.http.router.response.StatusCodeException;
import be.nille.http.router.route.MatchedRequest;
import be.nille.http.router.route.DefaultRoute;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class RequestHandler extends ChannelInboundHandlerAdapter {


    private final RouteRegistry registry;
    private final ExceptionHandler exceptionHandler;

    public RequestHandler(final RouteRegistry registry, final ExceptionHandler exceptionHandler) {
        this.registry = registry;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        
        if (msg instanceof Request.MetaData) {
            ctx.fireChannelRead(msg);
        }

        if (msg instanceof Request) {
            Response response;
            //do some interception of request
            Request request = (Request) msg;
            try {
                DefaultRoute route = registry.find(request.getMethod(), request.getPath().getValue());
                log.info(String.format("Route found with method %s and path %s", route.getMethod(), route.getPath()));
                Request matchedRequest = new MatchedRequest(route, request);
                response = route.execute(matchedRequest);

            } catch (StatusCodeException sce) {
                response = exceptionHandler.handleException(
                        new HttpRouterException(
                                new HttpRouterException.Context(sce.getStatusCode(), request),
                                sce
                        )
                );
            } catch (Exception ex) {
                HttpRouterException hre = new HttpRouterException(
                        new HttpRouterException.Context(new StatusCode(
                                        StatusCode.INTERNAL_SERVER_ERROR),
                                request
                        ),
                        ex);
                response = exceptionHandler.handleException(hre);
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
