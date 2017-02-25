/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;


import be.nille.http.router.media.TextMedia;
import be.nille.http.router.v2.response.RouterResponse;
import be.nille.http.router.v2.request.RouterRequest;
import be.nille.http.router.v2.response.Response;
import be.nille.http.router.v2.response.StatusCodeException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 *
 * @author Niels Holvoet
 */
@Slf4j
public class RouterTest {
    
    @Test
    public void testApi() throws URISyntaxException, StatusCodeException{
        Router router = new Router();
        router.add(new Route(new MyRouteCallback()));
        
        RouterRequest request = new RouterRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscribers?bla=test&bla=test2"),
                new TextMedia("hello"),
                new HashMap<>()
        );
        router.evaluate(request);
    }
    
    private static class MyRouteCallback implements RouteCallback{

        @Override
        public Response handle(RouterRequest request) {
            request
                    .queryParameters()
                    .list("bla")
                    .stream()
                    .forEach(v -> log.debug(v));
            return new RouterResponse();
        }
        
    }
    
}
