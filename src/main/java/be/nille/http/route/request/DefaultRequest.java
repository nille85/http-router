/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route.request;


import be.nille.http.route2.Method;
import be.nille.http.route2.Route;
import io.netty.handler.codec.http.QueryStringDecoder;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Getter
@Slf4j
public class DefaultRequest implements Request {
    
    private final Method method;
    
    private final URI uri;
  
    private final Map<String,String> headers;
    
    private final Route route;
    
    private final Body body;
       
    public DefaultRequest(final Method method, final URI uri, final Body body,
            Map<String,String> headers, final Route route){
        this.method = method;
        this.uri = uri;
        this.headers = headers;
        this.body = body;
        this.route = route;
    }
    
    
    @Override
    public Map<String,List<String>> getQueryParameters(){
      QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
      Map<String, List<String>> params = queryStringDecoder.parameters();
      return params;
    }
    
    
    @Override
    public Map<String,String> getPathParameters(){
       
        Map<String,String> pathParams = new HashMap<>();
        List<String> keys = getKeys();
        List<String> values = getValues(this);
        for(int i=0; i<keys.size();i++){
            pathParams.put(keys.get(i), values.get(i));
        }
        
        return pathParams;
    }
    
     private List<String> getKeys(){
        
        final String pathValue = route.getPath().getValue();
         
        Pattern pattern = Pattern.compile(":([^:/]*)/?");
        Matcher matcher = pattern.matcher(pathValue);
        List<String> keys = new ArrayList<>();
        while (matcher.find()) {
            String variable = matcher.group(1);
            keys.add(variable);
        }
        return keys;
     }

    private List<String> getValues(Request request) {
        List<String> values = new ArrayList<>();
        final String pathValue = route.getPath().getValue();
        String pathRegex = pathValue.replaceAll(":([^:/]*)", "(.*)");
        log.debug(pathRegex);
        Pattern pattern = Pattern.compile(pathRegex);
        final String requestPath = request.getUri().getPath();
        Matcher matcher = pattern.matcher(requestPath);
        if(matcher.matches()){
            for(int i=1; i<=matcher.groupCount();i++){
                values.add(matcher.group(i));
            }
        }
        return values;
    }

  

    
    
    
    
}
