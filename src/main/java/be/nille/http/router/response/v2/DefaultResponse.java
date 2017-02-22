/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.response.v2;

import be.nille.http.router.media.Media;
import be.nille.http.router.media.TextMedia;
import be.nille.http.router.response.StatusCode;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import lombok.ToString;

/**
 *
 * @author nholvoet
 */

@ToString
public final class DefaultResponse implements Response {
    
    private final StatusCode statusCode;
    private final Media body;
    private final Map<String,String> headers;
    
  
    
    public DefaultResponse(){
        this(new StatusCode(200), new TextMedia(""), new HashMap<>());
    }
    
    public DefaultResponse(final StatusCode statusCode, final Media body, final Map<String,String> headers){
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }
    
    public DefaultResponse statusCode(final int statusCode){
        return new DefaultResponse(new StatusCode(statusCode), this.body, this.headers);
    }
    
    public DefaultResponse body(final Media body){
        return new DefaultResponse(this.statusCode, body, this.headers);
    }
    
    public DefaultResponse header(final String key, final String value){
        Map<String,String> copy = headers;
        copy.put(key, value);
        return new DefaultResponse(this.statusCode, body, copy);
    }

    @Override
    public Media getBody() {
        return this.body;
    }

    @Override
    public StatusCode getStatusCode() {
       return this.statusCode;
    }

    @Override
    public Map<String, String> getHeaders() {
        return ImmutableMap.copyOf(headers);
    }
    
}
