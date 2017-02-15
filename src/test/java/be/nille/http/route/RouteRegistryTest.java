/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

import be.nille.http.route.request.Request;
import be.nille.http.route.response.DefaultResponse;
import be.nille.http.route.response.Response;
import be.nille.http.route.response.Response.Body;
import be.nille.http.route2.RequestHandler;
import be.nille.http.route2.Route;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class RouteRegistryTest {
    
   
    
    @Test
    public void testRegistry(){
        RouteRegistry registry = new RouteRegistry(new HttpRouter())
                .add(new Route("GET", "/subscriptions/{subscriptionId}",new MyRequestHandler())
                )
                .add(new Route("POST", "/subscriptions",new MyRequestHandler())
                );
        
       assertTrue(registry.getRoutes().size() == 2);
        
        
    }
    
    
    private static class MyRequestHandler implements RequestHandler{

        @Override
        public Response handle(Request request) {
            return new DefaultResponse(new Body("this is a simple response"));
        }
        
    }
    
}
