/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.request.Request;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;

/**
 *
 * @author nholvoet
 */
@Getter
public class MatchedRequest implements Request {
    
    private final DefaultRoute route;
    private final Request request;
    
    public MatchedRequest(final DefaultRoute route, final Request request){
        this.request = request;
        this.route = route;
    }
    
    @Override
    public Map<String,String> getPathParameters(){
       
        Map<String,String> pathParams = new HashMap<>();
        List<String> keys = getKeys(route);
        List<String> values = getValues(request, route);
        
        for(int i=0; i<keys.size();i++){
            String key = keys.get(i);
            String value = values.get(i);
            pathParams.put(key, value);
        }
        
        return pathParams;
    }
    
     private List<String> getKeys(DefaultRoute route){
        
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

    private List<String> getValues(Request request, DefaultRoute route) {
        List<String> values = new ArrayList<>();
        final String pathValue = route.getPath().getValue();
        String pathRegex = pathValue.replaceAll(":([^:/]*)", "(.*)");
      
        Pattern pattern = Pattern.compile(pathRegex);
        final String requestPath = request.getPath().getValue();
        Matcher matcher = pattern.matcher(requestPath);
        if(matcher.matches()){
            for(int i=1; i<=matcher.groupCount();i++){
                values.add(matcher.group(i));
            }
        }
        return values;
    }

    @Override
    public Method getMethod() {
        return request.getMethod();
    }

   

    @Override
    public Map<String, List<String>> getQueryParameters() {
        return request.getQueryParameters();
    }

   

    @Override
    public Map<String, String> getHeaders() {
        return request.getHeaders();
    }

    @Override
    public Body getBody() {
        return request.getBody();
    }
    
    
    @Override
    public String toString(){
        return String.format("Request[ method : %s, path: %s", request.getMethod(), request.getPath());
    }

    @Override
    public Path getPath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
}
