/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.request;


import be.nille.http.router.media.Media;
import be.nille.http.router.media.TextMedia;
import be.nille.http.router.v2.route.Method;
import com.google.common.collect.ImmutableMap;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */

@Slf4j
public final class RouterRequest implements Request{
   
    @Getter
    private final Method method;
    
    private final URI uri;
  
    private final Map<String,String> headers;
    @Getter
    private final Media body;
    
    
    public RouterRequest(final Method method, final URI uri){
        this(method, uri, new TextMedia(""),  ImmutableMap.of());
    }
   
    public RouterRequest(final Method method, final URI uri, final Media body,
            Map<String,String> headers){
        this.method = method;
        this.uri = uri;
        this.headers = headers;
        this.body = body;
    }
    
    
    @Override
    public QueryParameters queryParameters(){
      return new QueryParameters(uri);
    }

    @Override
    public String getPath(){
        return uri.getPath();
    }

    @Override
    public Map<String, String> getHeaders() {
        return ImmutableMap.copyOf(headers);
    }

 
}
