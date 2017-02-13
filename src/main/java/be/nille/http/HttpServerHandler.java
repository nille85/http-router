/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http;

import be.nille.http.route.MethodNotAllowedException;
import be.nille.http.route.Request;
import be.nille.http.route.ResourceNotFoundException;
import be.nille.http.route.Route;
import be.nille.http.route.RouteRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import static io.netty.channel.SelectStrategy.CONTINUE;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
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
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.ServerCookieEncoder;
import io.netty.util.CharsetUtil;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
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
              log.info("HTTP Request received through channel");
              this.httpRequest = (HttpRequest) msg;
          }
  
         if (msg instanceof HttpContent) {
             log.info("HTTP Content received through channel");
             this.httpContent = (HttpContent) msg;
         
           
             if (msg instanceof LastHttpContent) {
                 log.info("Last HTTP Content received through channel");
                
                 LastHttpContent trailer = (LastHttpContent) msg;
                 Request request = new Request(httpRequest, httpContent);
                 StringBuilder sb = new StringBuilder();
                 try{
                    Route route = registry.find(request);
                    String output = route.getHandler().handle(request);
                    
                    sb.append("ROUTE FOUND: ").append(route.toString()).append("\n");
                    sb.append("OUTPUT: ").append(output);
                 }catch(MethodNotAllowedException ex){
                     sb.append("METHOD NOT ALLOWED");
                 }
                 catch(ResourceNotFoundException ex){
                      sb.append("RESOURCE NOT FOUND");
                 }
              
                 if (!writeResponse(trailer, ctx, sb.toString())) {
                     // If keep-alive is off, close the connection once the content is fully written.
                     ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                 }
             }
         }
               
     }
     
     private boolean writeResponse(HttpObject currentObj, ChannelHandlerContext ctx, String content) {
         // Decide whether to close the connection or not.
         boolean keepAlive = HttpHeaders.isKeepAlive(httpRequest);
         // Build the response object.
         FullHttpResponse response = new DefaultFullHttpResponse(
                 HTTP_1_1, currentObj.decoderResult().isSuccess()? OK : BAD_REQUEST,
                 Unpooled.copiedBuffer(content, CharsetUtil.UTF_8));
 
         response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
 
         if (keepAlive) {
             // Add 'Content-Length' header only for a keep-alive connection.
             response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
             // Add keep alive header as per:
             // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
             response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
         }
 
         // Encode the cookie.
         String cookieString = httpRequest.headers().get(COOKIE);
         if (cookieString != null) {
             Set<Cookie> cookies = CookieDecoder.decode(cookieString);
             if (!cookies.isEmpty()) {
                 // Reset the cookies if necessary.
                 for (Cookie cookie: cookies) {
                     response.headers().add(SET_COOKIE, ServerCookieEncoder.encode(cookie));
                 }
             }
         } else {
             // Browser sent no cookie.  Add some.
             response.headers().add(SET_COOKIE, ServerCookieEncoder.encode("key1", "value1"));
             response.headers().add(SET_COOKIE, ServerCookieEncoder.encode("key2", "value2"));
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