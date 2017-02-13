/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nholvoet
 */
public class RouteRegistry {
    
    private final List<Route> routes;
    
    public RouteRegistry(){
        routes = new ArrayList<>();
    }
    
    
    public void addRoute(final Route route){
        this.routes.add(route);
    }
    
   
    
}
