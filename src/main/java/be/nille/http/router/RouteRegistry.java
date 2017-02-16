/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router;

import be.nille.http.route.MethodNotFoundException;
import be.nille.http.route.exception.PathNotFoundException;
import be.nille.http.route.request.Request;
import be.nille.http.route2.Method;
import be.nille.http.route2.Route;
import java.net.URI;
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
   

    public RouteRegistry() {
        routes = new ArrayList<>();
       
    }
    

    public RouteRegistry add(final Route route) {
        this.routes.add(route);
        return this;
    }
    

    public Route find(Method method, URI requestURI) {
        List<Route> filteredRoutes
                = this.routes
                .stream()
                .filter(route -> route.matchesResource(requestURI.getPath()))
                .collect(Collectors.toList());
        
        if (!filteredRoutes.isEmpty()) {
            Optional<Route> optional = filteredRoutes.stream()
                    .filter(route -> route.matchesMethod(method))
                    .findFirst();
            return optional.orElseThrow(() -> new MethodNotFoundException(
                            String.format("The method %s at the URI path %s is not allowed", 
                                    method.getName(),
                                    requestURI.getPath())
                    ));
        }
       
        throw new PathNotFoundException(
                String.format("The resource at the URI path %s was not found", requestURI.getPath())
        );

    }

}
