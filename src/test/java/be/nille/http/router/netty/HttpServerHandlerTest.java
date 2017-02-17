/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import be.nille.http.route.exception.DefaultExceptionHandler;
import be.nille.http.router.RouteRegistry;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.string.StringDecoder;
import static junit.framework.Assert.assertTrue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class HttpServerHandlerTest {
    
    
    
    

    @Ignore
    public void shouldReturnBadRequest() {
        
        EmbeddedChannel channel = new EmbeddedChannel(
                new HttpServerHandler(
                        new RouteRegistry(),
                        new DefaultExceptionHandler()
                )
        );
       
        DefaultHttpRequest httpObject = new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"/test");
        HttpUtil.setKeepAlive(httpObject, true);
        httpObject.setDecoderResult(DecoderResult.failure(new RuntimeException("an error occurred while decoding")));
        
        
        channel.writeInbound(httpObject);
        FullHttpResponse response = (FullHttpResponse) channel.readInbound();
        log.debug(response.toString());
        
    
        
      
    }

}
