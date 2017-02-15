/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route2;


import be.nille.http.route.request.Request;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Getter
@ToString
@Slf4j
public class Route {
    
    
    private final Method method;
    private final Path path;
    private final RequestHandler handler;
    
    
    public Route(final String method,final String path, final RequestHandler handler){
        this(new Method(method),new Path(path), handler);
    }
    
    public Route(final Method method, final Path path,  final RequestHandler handler){
        this.method = method;
        this.path = path;
        this.handler = handler;
    }
    
    
    public boolean matchesMethod(final Request request){
        return request.getMethod().equals(method);
    }
    
    
    public boolean matchesResource(final Request request){
        final String pathValue = path.getValue();
        String valueWithWildcards = pathValue.replaceAll(":.*/?", "(.*)");
        String patternString =  "^" + valueWithWildcards + "(\\?.*)?$";
        log.debug(patternString);
        Pattern pattern = Pattern.compile(patternString);
        final String requestPath = request.getUri().getPath();
        Matcher matcher = pattern.matcher(requestPath);
        boolean matches = matcher.matches();
        return matches;
    }
    
    
    public Map<String,String> getPathVariables(final Request request){
       
        Map<String,String> pathParams = new HashMap<>();
        List<String> keys = getKeys();
        List<String> values = getValues(request);
        for(int i=0; i<keys.size();i++){
            pathParams.put(keys.get(i), values.get(i));
        }
        
        return pathParams;
    }
    
     private List<String> getKeys(){
        
        final String pathValue = path.getValue();
         
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
        final String pathValue = path.getValue();
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
