/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nholvoet
 */
public class Request {
    
    private final HttpRequest origin;
    private final HttpContent content;
    
    public Request(final HttpRequest httpRequest, final HttpContent httpContent){
        this.origin = httpRequest;
        this.content = httpContent;
    }
    
    public String getURI(){
        return origin.uri();
    }
    
    public RequestMethod getMethod(){
        return new RequestMethod(origin.method().name());
    }
    
    public Map<String,String> getHeaders(){
        HttpHeaders headers = origin.headers();
      
        Map<String,String> copiedHeaders = new HashMap<>();
        if (!headers.isEmpty()) {
            for (Map.Entry<String, String> h: headers) {
                copiedHeaders.put(h.getKey(), h.getValue());
                
            }
        }
        return copiedHeaders;
    }
    
    public Map<String, List<String>> getRequestParameterMap(){
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(origin.uri());
        Map<String, List<String>> params = queryStringDecoder.parameters();
        return params;
    }
    
    public String getBody(){
        ByteBuf buff = content.content();
        if (buff.isReadable()) {
             return buff.toString(CharsetUtil.UTF_8);
        }
        return "";
        
    }
    
}
