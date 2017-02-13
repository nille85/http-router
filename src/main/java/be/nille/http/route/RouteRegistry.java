/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

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

    public RouteRegistry withRoute(final Route route) {
        this.routes.add(route);
        return this;
    }

    public Route find(Request request) {
        List<Route> filteredRoutes
                = this.routes
                .stream()
                .filter(route -> route.getPath().matches(request.getURI()))
                .collect(Collectors.toList());
        if (!filteredRoutes.isEmpty()) {
            Optional<Route> optional = filteredRoutes.stream()
                    .filter(route -> request.getMethod().equals(route.getMethod()))
                    .findFirst();
            return optional.orElseThrow(
                    () -> new MethodNotAllowedException(
                            String.format("The method %s at the URI %s is not allowed", 
                                    request.getMethod().getMethodName(),
                                    request.getURI())
                    ));
        }
       
        throw new ResourceNotFoundException(
                String.format("The resource at the URI %s was not found", request.getURI())
        );

    }

}
