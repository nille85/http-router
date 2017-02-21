/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.request.Request;
import be.nille.http.router.request.RequestComponent;
import be.nille.http.router.response.Response;
import java.util.List;

/**
 *
 * @author nholvoet
 */
public class SimpleRoute implements Route{
    
    private final List<RequestComponent> components;
    private final RequestHandler successHandler;
    
    public SimpleRoute(final List<RequestComponent> components, final RequestHandler successHandler){
        this.components = components;
        this.successHandler = successHandler;
    }

    @Override
    public boolean matches(Request request) {
       return components.stream()
               .allMatch(component -> component.matches(request));
    }
    
    public Response execute(final Request request) {
        return successHandler.handle(request);
    }
    
    
}
