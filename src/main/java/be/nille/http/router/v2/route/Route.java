/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;


import be.nille.http.router.v2.request.Request;
import be.nille.http.router.v2.request.RouterRequest;
import be.nille.http.router.v2.request.RequestMatcher;
import be.nille.http.router.v2.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nholvoet
 */
public final class Route {

    private final List<RequestMatcher> matchers;
    private final RouteCallback function;

    public Route(final RouteCallback function) {
        this(new ArrayList<>(), function);
    }

    public Route(final List<RequestMatcher> matchers, final RouteCallback function) {
        this.matchers = matchers;
        this.function = function;
    }
    
    public boolean matches(Request request) {
       return matchers.stream()
               .allMatch(matcher -> matcher.matches(request));
    }

    public Route addMatcher(final RequestMatcher matcher){
        List<RequestMatcher> copy = matchers;
        copy.add(matcher);
        return new Route(copy, this.function);
    }
    
    public Response execute(RouterRequest request){
        return function.execute(request);
                
    }
    
    
    public List<String> variables(final Request request) {
        List<String> variables = new ArrayList<>();
        
        
       
        
        
        return variables;
    }
   
    
}
