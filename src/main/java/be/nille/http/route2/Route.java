/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route2;


import be.nille.http.route.request.Request;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author nholvoet
 */
@Getter
@ToString
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
    
    public static RouteBuilder builder(){
        return new RouteBuilder();
    }
    
    
    public boolean matchesMethod(final Request request){
        return request.getMethod().equals(method);
    }
    
    
    public boolean matchesResource(final Request request){
        final String pathValue = path.getValue();
        String valueWithWildcards = pathValue.replaceAll("\\{.*\\}", "(.*)");
        String patternString =  "^" + valueWithWildcards + "(\\?.*)?$";
        Pattern pattern = Pattern.compile(patternString);
        final String requestPath = request.getUri().getPath();
        Matcher matcher = pattern.matcher(requestPath);
        boolean matches = matcher.matches();
        return matches;
    }
    
    
    
    
    
    
    
    
}
