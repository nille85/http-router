/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

/**
 *
 * @author nholvoet
 */
public class RouteRegistryTest {
    
    public static class SimpleHandler implements RouteHandler{

        @Override
        public String handle(Request request) {
            return request.getURI();
        }
        
        
        
    }
    
    
    public void testRegistry(){
        RouteRegistry registry = new RouteRegistry()
                .withRoute(new Route(
                        new Path("/subscriptions/{subscriptionId"), new RequestMethod("GET"),
                        new SimpleHandler()
                    )
                )
                .withRoute(new Route(
                        new Path("/subscriptions"), new RequestMethod("POST"),
                         new SimpleHandler()
                    )
                );
        
       
        
        
    }
    
}
