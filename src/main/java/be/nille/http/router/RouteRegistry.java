/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router;

import be.nille.http.router.route.Method;
import be.nille.http.router.route.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
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
    
    public Route find(Method method, String requestPath) {
        List<Route> filteredByPath = findRoutesByPath(this.routes,requestPath);
        List<Route> filteredByMethod = findRoutesByMethod(filteredByPath, method);
        return filteredByMethod.get(0);
    }
    
    
    private List<Route> findRoutesByPath(List<Route> routes, String requestPath){
        List<Route> filteredRoutes
                = routes
                .stream()
                .filter(route -> route.matchesResource(requestPath))
                .collect(Collectors.toList());
       
        log.debug("matching routes by path");
        filteredRoutes.stream().forEach(route -> log.debug(route.toString()));
                
        if (!filteredRoutes.isEmpty()) {
            return filteredRoutes;
        }
        throw new PathNotFoundException(
                String.format("The resource at the path %s was not found", requestPath)
        );
        
    }
    
    private List<Route> findRoutesByMethod(List<Route> routes, Method method){
        List<Route> filteredRoutes
                = routes
                .stream()
                .filter(route -> route.matchesMethod(method))
                .collect(Collectors.toList());
        log.debug("matching routes by method and path");      
        filteredRoutes.stream().forEach(route -> log.debug(route.toString()));        
        if (!filteredRoutes.isEmpty()) {
            return filteredRoutes;
        }
        throw new MethodNotFoundException(
                String.format("The method at the current path %s is not allowed", 
                                    method.getName()
                                  
                )
        );
        
    }

}
