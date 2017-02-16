/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route.request;

import be.nille.http.route.request.Request.Body;
import be.nille.http.router.route.Method;
import be.nille.http.router.route.Route;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nholvoet
 */
public class RequestBuilder {
    
    private Method method;
    
    private URI uri;
  
    private final Map<String,String> headers;
    
    
    
    private Request.Body body;
    
    public RequestBuilder(){
        this.body = new Body("");
        this.headers = new HashMap<>();
        
    }
    
    
    public RequestBuilder withMethod(final String method){
        this.method = new Method(method);
        return this;
    }
    
    public RequestBuilder withURI(final String uri){
        try{
            this.uri = new URI(uri);
            return this;
        }catch(URISyntaxException ex){
            throw new IllegalArgumentException(
                    String.format("Uri %s is not a valid URI",uri)
            );
        }
    }
    
    public RequestBuilder withBody(final String body){
        this.body = new Body(body);
        return this;
    }
    
    public RequestBuilder withHeader(final String key, final String value){
        headers.put(key, value);
        return this;
    }
   
    
    
    public Request build(){
        validate();
        return new DefaultRequest(this.method, this.uri,this.body, headers);
    }
    
    private void validate(){
        checkNotNull(method, "method is a required field");
        checkNotNull(uri, "uri is a required field");
        checkArgument(body.getValue() != null, "body is a required field");
    }
    
}
