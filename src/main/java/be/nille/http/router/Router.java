/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router;

import be.nille.http.router.route.Route;
import java.util.List;

/**
 *
 * @author nholvoet
 */
public interface Router {
    
    void start();
    
    void addRoute(Route route);
    
    List<Route> getRoutes();
    
    
}
