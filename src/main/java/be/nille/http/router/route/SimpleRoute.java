/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.request.Request;
import be.nille.http.router.request.RequestMatcher;
import be.nille.http.router.response.Response;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nholvoet
 */
public class SimpleRoute implements OldRoute{
    
    private final List<RequestMatcher> matchers;
    private final RouteCallback successHandler;
    
    public SimpleRoute(final RouteCallback successHandler){
        this(new ArrayList<>(), successHandler); 
    }
    
    public SimpleRoute(final List<RequestMatcher> matchers, final RouteCallback successHandler){
        this.matchers = matchers;
        this.successHandler = successHandler;
    }
    
    
    @Override
    public OldRoute addMatcher(final RequestMatcher matcher){
        List<RequestMatcher> copiedMatchers = matchers;
        copiedMatchers.add(matcher);
        return new SimpleRoute(copiedMatchers, this.successHandler);
    }
    
    

    @Override
    public boolean matches(Request request) {
       return matchers.stream()
               .allMatch(matcher -> matcher.matches(request));
    }
    
    @Override
    public Response execute(final Request request) {
        return successHandler.handle(request);
    }

   

    
    
    
}
