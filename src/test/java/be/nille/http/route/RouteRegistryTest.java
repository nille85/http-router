/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class RouteRegistryTest {
    
   
    
    @Test
    public void testRegistry(){
        RouteRegistry registry = new RouteRegistry()
                .withRoute(new Route("/subscriptions/{subscriptionId}","GET",(request) -> new SimpleResponse(request.toString(),"text/plain; charset=UTF-8"))
                )
                .withRoute(new Route("/subscriptions","POST",(request) -> new SimpleResponse(request.toString(),"text/plain; charset=UTF-8"))
                );
        
       assertTrue(registry.getRoutes().size() == 2);
        
        
    }
    
}
