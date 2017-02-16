/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;


import be.nille.http.route.request.Request;
import be.nille.http.route.response.Response;
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

@ToString
@Slf4j
public class Route {
    
    @Getter
    private final Method method;
    @Getter
    private final Path path;
    private final RequestHandler successHandler;
    
    
    public Route(final String method,final String path, final RequestHandler successHandler){
        this(new Method(method),new Path(path), successHandler);
    }
    
    public Route(final Method method, final Path path,  final RequestHandler successHandler){
        this.method = method;
        this.path = path;
        this.successHandler = successHandler;
    }
    
    public Response execute(final Request request){
        return successHandler.handle(request);
    }
    
    
    public boolean matchesMethod(final Method method){
        return method.equals(this.method);
    }
    
    
    public boolean matchesResource(final String requestPath){
        final String pathValue = path.getValue();
        String valueWithWildcards = pathValue.replaceAll(":.*/?", "(.*)");
        String patternString =  "^" + valueWithWildcards + "(\\?.*)?$";
        log.debug(patternString);
        Pattern pattern = Pattern.compile(patternString);
       
        Matcher matcher = pattern.matcher(requestPath);
        boolean matches = matcher.matches();
        return matches;
    }
    
    
   
    
    
    
    
    
    
    
    
}
