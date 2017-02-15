/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

import be.nille.http.route.MethodNotAllowedException;
import be.nille.http.route.ResourceNotFoundException;
import be.nille.http.route.request.Request;
import be.nille.http.route2.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 *
 * @author nholvoet
 */
public class RouteRegistry {

    @Getter
    private final List<Route> routes;
    private HttpRouter router;

    public RouteRegistry(final HttpRouter router) {
        routes = new ArrayList<>();
        this.router = router;
    }
    

    public RouteRegistry add(final Route route) {
        this.routes.add(route);
        return this;
    }
    
    public HttpRouter save(){
        return router;
    }

    public Route find(Request request) {
        List<Route> filteredRoutes
                = this.routes
                .stream()
                .filter(route -> route.matchesResource(request))
                .collect(Collectors.toList());
        
        if (!filteredRoutes.isEmpty()) {
            Optional<Route> optional = filteredRoutes.stream()
                    .filter(route -> route.matchesMethod(request))
                    .findFirst();
            return optional.orElseThrow(
                    () -> new MethodNotAllowedException(
                            String.format("The method %s at the URI %s is not allowed", 
                                    request.getMethod().getName(),
                                    request.getUri().toString())
                    ));
        }
       
        throw new ResourceNotFoundException(
                String.format("The resource at the URI %s was not found", request.getUri().toString())
        );

    }

}
