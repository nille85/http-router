/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router;

import be.nille.http.router.RouteRegistry;
import be.nille.http.router.request.Request;
import be.nille.http.router.response.Response;
import be.nille.http.router.route.Method;
import be.nille.http.router.route.RequestHandler;
import be.nille.http.router.route.Route;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class RouteRegistryTest {
    
   private RouteRegistry registry;
   
   @Before
   public void setup(){
       registry = new RouteRegistry()
                .add(new Route("GET", "/subscriptions/:subscriptionId",new TestRequestHandler())
                )
                .add(new Route("POST", "/subscriptions",new TestRequestHandler())
                );
   }
    
    @Test
    public void shouldFindRegisteredRoute(){
          
       Route route = registry.find(new Method(Method.GET),"/subscriptions/20");
       assertTrue(route.matchesMethod(new Method(Method.GET)));
       assertTrue(route.matchesResource("/subscriptions/20"));
            
    }
    
    @Test(expected = MethodNotFoundException.class)
    public void shouldThrowMethodNotFoundException(){
       registry.find(new Method(Method.PUT),"/subscriptions/20");
    }
    
    @Test(expected = PathNotFoundException.class)
    public void shouldThrowPathNotFoundException(){
       registry.find(new Method(Method.PUT),"/persons/20");
    }
    
    
    private static class TestRequestHandler implements RequestHandler{

        @Override
        public Response handle(Request request) {
            return Response.builder()
                    .withBody("this is a simple response")
                    .build();
        }
        
    }
    
}
