/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;




import be.nille.http.router.media.TextMedia;

import be.nille.http.router.v2.request.RouterRequest;
import be.nille.http.router.v2.response.RouterResponse;
import be.nille.http.router.v2.response.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class RouteTest {
    
    @Test
    public void routeShouldMatchRequestWhenMethodAndPathAreTheSameAsInRequest() throws URISyntaxException{
       
        Route route = new Route(new MyCallback())
                .addMatcher(new Method(Method.GET))
                .addMatcher(new EqualPath("/subscribers"));
        
        RouterRequest request = new RouterRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscribers"),
                new TextMedia("hello"),
                new HashMap<>()
        );
                
       
        assertTrue(route.matches(request));
    }
    
    
    private static class MyCallback implements RouteCallback{

        @Override
        public Response handle(RouterRequest request) {
           return new RouterResponse();
        }
    }
     
}
