/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route.request;


import be.nille.http.route2.Method;
import io.netty.handler.codec.http.QueryStringDecoder;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 *
 * @author nholvoet
 */
@Getter
public class ImmutableRequest implements Request {
    
    private final Method method;
    
    private final URI uri;
  
    private final Map<String,String> headers;
    
    private final Body body;
    
    
    public ImmutableRequest(final URI uri){
        this(new Method(Method.GET), uri, new Body(""), new HashMap<>());
    }
    
    public ImmutableRequest(final Method method, final URI uri, final Body body, Map<String,String> headers){
        this.method = method;
        this.uri = uri;
        this.headers = headers;
        this.body = body;
    }
    
    
    @Override
    public Map<String,List<String>> getQueryParameters(){
      QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
      Map<String, List<String>> params = queryStringDecoder.parameters();
      return params;
    }

    
    
    
    
}
