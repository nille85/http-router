/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.request;


import be.nille.http.router.route.Method;
import be.nille.http.router.route.Path;
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
public class DefaultRequest implements Request {
   
    @Getter
    private final Method method;
    @Getter
    private final Path path;
    @Getter
    private final Map<String,String> headers;
    @Getter
    private final Body body;
    
    private final QueryStringDecoder queryStringDecoder;
       
    public DefaultRequest(final Method method, final URI uri, final Body body,
            Map<String,String> headers){
        this.method = method;
        this.queryStringDecoder = new QueryStringDecoder(uri);
        this.path = new Path(uri.getPath());
        this.headers = headers;
        this.body = body;
    }
    
    
    @Override
    public Map<String,List<String>> getQueryParameters(){
      Map<String, List<String>> params = queryStringDecoder.parameters();
      return params;
    }

    @Override
    public Map<String, String> getPathParameters() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
}
