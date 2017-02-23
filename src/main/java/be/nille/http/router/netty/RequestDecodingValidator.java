/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import be.nille.http.router.exception.ExceptionHandler;
import be.nille.http.router.HttpRouterException;
import be.nille.http.router.HttpRouterException.Context;

import be.nille.http.router.RouteRegistry;
import be.nille.http.router.v2.response.StatusCodeException;
import be.nille.http.router.media.TextMedia;
import be.nille.http.router.request.Request;
import be.nille.http.router.response.Body;
import be.nille.http.router.response.Response;
import be.nille.http.router.response.StatusCode;
import be.nille.http.router.route.MatchedRequest;
import be.nille.http.router.route.DefaultRoute;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class RequestDecodingValidator extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof HttpObject) {

            HttpObject object = (HttpObject) msg;
            boolean decodingSuccess = object.decoderResult().isSuccess();
            if (decodingSuccess) {
                ctx.fireChannelRead(object);
            } else {
                FullHttpResponse response = new DefaultFullHttpResponse(
                        HTTP_1_1, HttpResponseStatus.valueOf(400),
                        Unpooled.copiedBuffer("", CharsetUtil.UTF_8));

                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }

}
