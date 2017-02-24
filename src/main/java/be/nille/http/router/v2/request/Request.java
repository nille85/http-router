/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.request;


import be.nille.http.router.media.Media;
import be.nille.http.router.route.Path;
import be.nille.http.router.v2.route.Method;

import io.netty.handler.codec.http.QueryStringDecoder;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */

@Slf4j
public class Request {
   
    @Getter
    private final Method method;
    
    private final URI uri;
    @Getter
    private final Map<String,String> headers;
    @Getter
    private final Media body;
    
   
    public Request(final Method method, final URI uri, final Media body,
            Map<String,String> headers){
        this.method = method;
        this.uri = uri;
        this.headers = headers;
        this.body = body;
    }
    
    
    public QueryParameters queryParameters(){
      return new QueryParameters(uri);
    }

    public String getPath(){
        return uri.getPath();
    }

 
}
