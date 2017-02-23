/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import be.nille.http.router.exception.ExceptionHandler;
import be.nille.http.router.HttpRouterException;
import be.nille.http.router.RouteRegistry;
import be.nille.http.router.v2.response.StatusCodeException;
import be.nille.http.router.request.Request;
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
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.util.CharsetUtil;
import java.nio.charset.Charset;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class ResponseWriter extends ChannelInboundHandlerAdapter {

    private Request.MetaData metaData;

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof Request.MetaData) {
            metaData = (Request.MetaData) msg;
        }

        if (msg instanceof Response) {
            Response response = (Response) msg;

            log.info("creating netty response");
            FullHttpResponse nettyResponse = new DefaultFullHttpResponse(
                    HTTP_1_1, HttpResponseStatus.valueOf(response.getStatusCode().getValue()),
                    Unpooled.copiedBuffer(response.getBody().print(), metaData.getCharset())
            );

            if (metaData.isKeepAlive()) {
                // Add 'Content-Length' header only for a keep-alive connection.
                nettyResponse.headers().set(CONTENT_LENGTH, nettyResponse.content().readableBytes());
                // Add keep alive header as per:
                // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
                nettyResponse.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            }

            for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
                nettyResponse.headers().set(header.getKey(), header.getValue());
            }

            ctx.write(nettyResponse);

            if (!metaData.isKeepAlive()) {
                ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            } else {
                ctx.flush();
            }

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }

}
