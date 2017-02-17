/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class MyRequestDecoderTest {
    
    
    MyRequestDecoder decoder;
    
    @Before
    public void setup(){
        decoder = new MyRequestDecoder();
    }
    
    @Test
    public void httpRequestShouldRemainUnchanged(){
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        DefaultHttpRequest original = new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"/test");
       
        channel.writeInbound(original);
        HttpObject channeledObject = (HttpObject) channel.readInbound();
        assertEquals(channeledObject, original);
        
        final ByteBuf buf = Unpooled.copiedBuffer("Hello world".getBytes());
        HttpContent content = new DefaultHttpContent(buf);
    }
    
    @Test
    public void httpContentShouldRemainUnchanged(){
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        final ByteBuf buf = Unpooled.copiedBuffer("Hello world".getBytes());
        HttpContent content = new DefaultHttpContent(buf);
       
        channel.writeInbound(content);
        HttpContent channeledObject = (HttpContent) channel.readInbound();
        assertEquals(channeledObject, content);
        ByteBuf cont = content.content();
        int i=0;
        while(cont.isReadable()){
            byte b = cont.readByte();
            assertEquals(b, "Hello world".getBytes()[i]);
            i++;
        }
        
    }
    
}
