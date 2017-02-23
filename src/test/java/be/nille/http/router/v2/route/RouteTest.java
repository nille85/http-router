/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;

import be.nille.http.router.media.TextMedia;
import be.nille.http.router.request.Request;
import be.nille.http.router.response.Response;
import be.nille.http.router.route.Method;
import be.nille.http.router.route.Path;
import be.nille.http.router.route.Route;
import be.nille.http.router.route.RouteCallback;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class RouteTest {
    
    @Test
    public void routeShouldMatchRequestWhenMethodAndPathAreTheSameAsInRequest(){
       
        Route route = new Route(new MyCallback())
                .addMatcher(new Method(Method.GET))
                .addMatcher(new Path("/subscribers"));
        
        
                
        Request request = Request.builder()
                .withMethod(Method.GET)
                .withURI("http://localhost:8080/subscribers")
                .build();
        assertTrue(route.matches(request));
    }
    
    
    private static class MyCallback implements RouteCallback{

        @Override
        public Response handle(Request request) {
           return Response.builder()
                   .withBody(new TextMedia("sdlkf"))
                   .build();
        }
    }
     
}
