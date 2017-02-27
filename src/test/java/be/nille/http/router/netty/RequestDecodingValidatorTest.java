/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpVersion;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Niels Holvoet
 */
public class RequestDecodingValidatorTest {
    
    
    RequestDecodingValidator validator;
    
    @Before
    public void setup(){
        validator = new RequestDecodingValidator();
    }
    
    @Test
    public void shouldWriteBadRequestResponseWhenDecodingFailed(){
        EmbeddedChannel channel = new EmbeddedChannel(validator);
        DefaultHttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"/test");
        request.setDecoderResult(DecoderResult.failure(new RuntimeException("Decoding failed")));
        channel.writeInbound(request);
        FullHttpResponse response = (FullHttpResponse) channel.readOutbound();
        assertEquals(400, response.status().code());
        
    }
    
    @Test
    public void shouldBeChanneledWhenDecodingWasSuccessfull(){
        EmbeddedChannel channel = new EmbeddedChannel(validator);
        DefaultHttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"/test");
        request.setDecoderResult(DecoderResult.SUCCESS);
        channel.writeInbound(request);
        HttpObject channeledObject = (HttpObject) channel.readInbound();
        assertEquals(channeledObject, request);
        
    }
    
}
