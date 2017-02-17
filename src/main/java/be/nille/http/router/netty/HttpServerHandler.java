/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import be.nille.http.route.exception.ExceptionHandler;
import be.nille.http.router.HttpRouterException;
import be.nille.http.router.HttpRouterException.Context;

import be.nille.http.router.RouteRegistry;
import be.nille.http.router.StatusCodeException;
import be.nille.http.router.media.TextMedia;
import be.nille.http.router.request.Request;
import be.nille.http.router.response.Body;
import be.nille.http.router.response.Response;
import be.nille.http.router.response.StatusCode;
import be.nille.http.router.route.MatchedRequest;
import be.nille.http.router.route.Route;
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
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
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
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    private final RouteRegistry registry;
    private final ExceptionHandler exceptionHandler;

    private HttpRequest httpRequest;
    private HttpContent httpContent;

    public HttpServerHandler(final RouteRegistry registry, final ExceptionHandler exceptionHandler) {
        this.registry = registry;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Response response;

        if (msg instanceof HttpRequest) {

            this.httpRequest = (HttpRequest) msg;
            boolean decodingSuccess = httpRequest.decoderResult().isSuccess();
            if (!decodingSuccess) {
                response = Response.builder()
                        .withBody(new Body(new TextMedia("")))
                        .withHeader("content-type", "text-plain")
                        .withStatusCode(StatusCode.BAD_REQUEST)
                        .build();
                if (!writeResponse(ctx, response)) {
                    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                }
            }
        }

        if (msg instanceof HttpContent) {
            log.info("HTTP Content received through channel");
            this.httpContent = (HttpContent) msg;

            if (msg instanceof LastHttpContent) {

                LastHttpContent trailer = (LastHttpContent) msg;

                Request nettyRequest = new NettyRequest(httpRequest, httpContent);
                try {
                    Route route = registry.find(nettyRequest.getMethod(), nettyRequest.getUri().getPath());
                    log.info(String.format("Route found with method %s and path %s", route.getMethod(), route.getPath()));
                    Request matchedRequest = new MatchedRequest(route, nettyRequest);
                    response = route.execute(matchedRequest);

                } catch (StatusCodeException sce) {
                    response = exceptionHandler.handleException(
                            new HttpRouterException(
                                    new Context(sce.getStatusCode(), nettyRequest),
                                    sce
                            )
                    );
                } catch (Exception ex) {
                    HttpRouterException hre = new HttpRouterException(
                            new Context(new StatusCode(
                                            StatusCode.INTERNAL_SERVER_ERROR),
                                    nettyRequest
                            ),
                            ex);
                    response = exceptionHandler.handleException(hre);
                }

                if (!writeResponse(ctx, response)) {
                    // If keep-alive is off, close the connection once the content is fully written.
                    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                }
            }
        }

    }

    private boolean writeResponse(ChannelHandlerContext ctx, Response resp) {
        // Decide whether to close the connection or not.
        boolean keepAlive = HttpHeaders.isKeepAlive(httpRequest);
        // Build the response object.
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, HttpResponseStatus.valueOf(resp.getStatusCode().getValue()),
                Unpooled.copiedBuffer(resp.getBody().print(), CharsetUtil.UTF_8));

        if (keepAlive) {
            // Add 'Content-Length' header only for a keep-alive connection.
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            // Add keep alive header as per:
            // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }

        response.setStatus(new HttpResponseStatus(resp.getStatusCode().getValue(), ""));

        for (Map.Entry<String, String> header : resp.getHeaders().entrySet()) {
            response.headers().set(header.getKey(), header.getValue());
        }

        // Write the response.
        ctx.write(response);

        return keepAlive;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }

}
