/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import be.nille.http.router.MethodNotFoundException;
import be.nille.http.router.PathNotFoundException;

import be.nille.http.router.RouteRegistry;
import be.nille.http.route.exception.HttpRouterException;
import be.nille.http.route.request.DefaultRequest;
import be.nille.http.route.request.Request;
import be.nille.http.route.response.DefaultResponse;
import be.nille.http.route.response.Response;
import be.nille.http.route.response.Response.StatusCode;
import be.nille.http.route2.Method;
import be.nille.http.route2.Route;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class HttpServerHandler extends SimpleChannelInboundHandler<Object> {

    private final RouteRegistry registry;
    private HttpRequest httpRequest;
    private HttpContent httpContent;

    public HttpServerHandler(final RouteRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest) {

            this.httpRequest = (HttpRequest) msg;
        }

        if (msg instanceof HttpContent) {
            log.info("HTTP Content received through channel");
            this.httpContent = (HttpContent) msg;

            if (msg instanceof LastHttpContent) {

                LastHttpContent trailer = (LastHttpContent) msg;
           
               
                Response response;
                try {
                    
                    Method method = new Method(httpRequest.method().name());
                    Request.Body body = new Request.Body(getRequestBody(httpContent));
                    
                    String requestPath = new URI(httpRequest.uri()).getPath();
                    Route route = registry.find(method,requestPath);//possibly throws exception
                    Request request = new DefaultRequest(
                            method, new URI(httpRequest.uri()), body, getHeaders(httpRequest), route
                    );
                    response = route.execute(request);
                    
                } catch (MethodNotFoundException ex) {
                    log.info(ex.getMessage());
                    response = new DefaultResponse(
                            new Response.Body("not allowed"), new StatusCode(StatusCode.METHOD_NOT_ALLOWED), getDefaultHeaders()
                    );
                   
                } catch (PathNotFoundException ex) {
                    log.info(ex.getMessage());
                    response = new DefaultResponse(
                            new Response.Body("not found"), new StatusCode(StatusCode.NOT_FOUND), getDefaultHeaders()
                    );

                } 
                
                catch (Exception ex) {
                    log.info(ex.getMessage());
                    response = new DefaultResponse(
                            new Response.Body(""), new StatusCode(StatusCode.INTERNAL_SERVER_ERROR), getDefaultHeaders()
                    );
                    
                   

                }

                if (!writeResponse(trailer, ctx, response)) {
                    // If keep-alive is off, close the connection once the content is fully written.
                    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                }
            }
        }

    }
    
    private Map<String,String> getDefaultHeaders(){
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html");
        return headers;
    }

    private boolean writeResponse(HttpObject currentObj, ChannelHandlerContext ctx, Response resp) {
        // Decide whether to close the connection or not.
        boolean keepAlive = HttpHeaders.isKeepAlive(httpRequest);
        // Build the response object.
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, currentObj.decoderResult().isSuccess() ? OK : BAD_REQUEST,
                Unpooled.copiedBuffer(resp.getBody().getValue(), CharsetUtil.UTF_8));

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

    private  String getRequestBody(HttpContent content) {
        ByteBuf buff = content.content();
        if (buff.isReadable()) {
            return buff.toString(CharsetUtil.UTF_8);
        }
        return "";
    }

    private Map<String, String> getHeaders(HttpRequest request) {
        HttpHeaders headers = request.headers();

        Map<String, String> copiedHeaders = new HashMap<>();
        if (!headers.isEmpty()) {
            for (Map.Entry<String, String> h : headers) {
                copiedHeaders.put(h.getKey(), h.getValue());

            }
        }
        return copiedHeaders;
    }
}
