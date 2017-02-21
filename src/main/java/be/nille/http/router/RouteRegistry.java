/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router;

import be.nille.http.router.response.StatusCode;
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
public final class RouteRegistry {

    @Getter
    private final List<Route> routes;
   
    public RouteRegistry() {
        routes = new ArrayList<>();    
    }
    
    private RouteRegistry(List<Route> routes){
        this.routes = routes;
    }
    
    public RouteRegistry add(final Route route) {
        List<Route> copiedRoutes = this.routes;
        copiedRoutes.add(route);
        return new RouteRegistry(copiedRoutes);
    }
    
    public Route find(Method method, String requestPath) throws StatusCodeException {
        List<Route> filteredByPath = findRoutesByPath(this.routes,requestPath);
        List<Route> filteredByMethod = findRoutesByMethod(filteredByPath, method);
        return filteredByMethod.get(0);
    }
    
    
    private List<Route> findRoutesByPath(List<Route> routes, String requestPath) throws StatusCodeException{
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
        throw new StatusCodeException(new StatusCode(StatusCode.NOT_FOUND));
        
    }
    
    private List<Route> findRoutesByMethod(List<Route> routes, Method method) throws StatusCodeException{
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
        throw new StatusCodeException(new StatusCode(StatusCode.METHOD_NOT_ALLOWED));
        
        
    }

}
