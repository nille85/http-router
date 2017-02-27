/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.request;


import be.nille.http.router.body.Body;
import be.nille.http.router.body.TextBody;
import be.nille.http.router.headers.Headers;

import java.net.URI;
import java.util.HashMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */

@Slf4j
public final class RouteRequest implements Request{
   
    @Getter
    private final Method method;
    
   
    private final URI uri;
  
    private final Headers headers;
    @Getter
    private final Body body;
    
    
    public RouteRequest(final Method method, final URI uri){
        this(method, uri, new TextBody(""),  new Headers());
    }
   
    public RouteRequest(final Method method, final URI uri, final Body body,
            Headers headers){
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
    public Headers getHeaders() {
        return headers;
    }

    @Override
    public PathVariables variables() {
       return new PathVariables(new HashMap<>());
    }

    @Override
    public URI getURI() {
        return uri;
    }

  

    

 
}
