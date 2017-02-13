/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http;

import be.nille.http.route.MethodNotAllowedException;
import be.nille.http.route.Request;
import be.nille.http.route.ResourceNotFoundException;
import be.nille.http.route.Response;
import be.nille.http.route.Route;
import be.nille.http.route.RouteRegistry;
import be.nille.http.route.SimpleResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderNames.COOKIE;
import static io.netty.handler.codec.http.HttpHeaderNames.SET_COOKIE;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.ServerCookieEncoder;
import io.netty.util.CharsetUtil;
import java.util.Map;
import java.util.Set;
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
      
      public HttpServerHandler(final RouteRegistry registry){
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
  
         if (msg instanceof HttpContent) { log.info("HTTP Content received through channel");
             this.httpContent = (HttpContent) msg;
         
           
             if (msg instanceof LastHttpContent) {
                      
                 LastHttpContent trailer = (LastHttpContent) msg;
                 Request request = new Request(httpRequest, httpContent);
                 StringBuilder sb = new StringBuilder();
                 Response response;
                 try{
                    Route route = registry.find(request);
                    response = route.getHandler().handle(request);
                 }catch(MethodNotAllowedException ex){
                     log.info(ex.getMessage());
                     response = new SimpleResponse("","text/html; charset=utf-8",405);
                     sb.append("METHOD NOT ALLOWED");
                 }
                 catch(ResourceNotFoundException ex){
                     log.info(ex.getMessage());
                     response = new SimpleResponse("","text/html; charset=utf-8",404);
                     
                 }
              
                 if (!writeResponse(trailer, ctx, response)) {
                     // If keep-alive is off, close the connection once the content is fully written.
                     ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                 }
             }
         }
               
     }
      
  
     
     private boolean writeResponse(HttpObject currentObj, ChannelHandlerContext ctx, Response resp) {
         // Decide whether to close the connection or not.
         boolean keepAlive = HttpHeaders.isKeepAlive(httpRequest);
         // Build the response object.
         FullHttpResponse response = new DefaultFullHttpResponse(
                 HTTP_1_1, currentObj.decoderResult().isSuccess()? OK : BAD_REQUEST,
                 Unpooled.copiedBuffer(resp.getContent(), CharsetUtil.UTF_8));
 
 
         if (keepAlive) {
             // Add 'Content-Length' header only for a keep-alive connection.
             response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
             // Add keep alive header as per:
             // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
             response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
         }
         
         response.setStatus(new HttpResponseStatus(resp.getStatusCode(), ""));
         
         for(Map.Entry<String,String> header : resp.getHeaders().entrySet()){
             response.headers().set(header.getKey(),header.getValue());
         }
 
         // Write the response.
         ctx.write(response);
         
 
         return keepAlive;
     }
 
    
     
 
     @Override
     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
         cause.printStackTrace();
         ctx.close();
     }
 }