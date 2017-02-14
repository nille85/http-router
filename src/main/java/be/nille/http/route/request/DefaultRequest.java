/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route.request;


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
public class DefaultRequest implements Request {
    
    private final URI uri;
    @Getter
    private final Map<String,String> headers;
    
    private final Body body;
    
    
    public DefaultRequest(final URI uri){
        this(uri, new Body(""), new HashMap<>());
    }
    
    public DefaultRequest(final URI uri, final Body body, Map<String,String> headers){
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

    @Override
    public Body getBody() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
