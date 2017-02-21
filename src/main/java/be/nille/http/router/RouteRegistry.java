/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router;

import be.nille.http.router.response.StatusCode;
import be.nille.http.router.route.Method;
import be.nille.http.router.route.DefaultRoute;
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
    private final List<DefaultRoute> routes;
   
    public RouteRegistry() {
        routes = new ArrayList<>();    
    }
    
    private RouteRegistry(List<DefaultRoute> routes){
        this.routes = routes;
    }
    
    public RouteRegistry add(final DefaultRoute route) {
        List<DefaultRoute> copiedRoutes = this.routes;
        copiedRoutes.add(route);
        return new RouteRegistry(copiedRoutes);
    }
    
    public DefaultRoute find(Method method, String requestPath) throws StatusCodeException {
        List<DefaultRoute> filteredByPath = findRoutesByPath(this.routes,requestPath);
        List<DefaultRoute> filteredByMethod = findRoutesByMethod(filteredByPath, method);
        return filteredByMethod.get(0);
    }
    
    
    private List<DefaultRoute> findRoutesByPath(List<DefaultRoute> routes, String requestPath) throws StatusCodeException{
        List<DefaultRoute> filteredRoutes
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
    
    private List<DefaultRoute> findRoutesByMethod(List<DefaultRoute> routes, Method method) throws StatusCodeException{
        List<DefaultRoute> filteredRoutes
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
