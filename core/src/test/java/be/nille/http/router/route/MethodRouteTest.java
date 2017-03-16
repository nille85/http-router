/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.route.MethodRoute;
import be.nille.http.router.route.Route;
import be.nille.http.router.body.TextBody;
import be.nille.http.router.request.Method;
import be.nille.http.router.request.Request;
import be.nille.http.router.request.RouteRequest;
import be.nille.http.router.response.Response;
import be.nille.http.router.response.RouteResponse;
import be.nille.http.router.response.StatusCode;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Niels Holvoet
 */
public class MethodRouteTest {
    
    private Route origin;
    
    @Before
    public void setup(){
        origin = (request) -> new RouteResponse(new StatusCode(StatusCode.OK), new TextBody("Hello World"));
    }
    
    
    @Test
    public void responseShouldBeNotEmptyWhenMethodsAreEqual() throws URISyntaxException{
        Route route = new MethodRoute(new Method(Method.GET), origin);
        Request request = new RouteRequest(new Method(Method.GET), new URI("http://localhost/test"));
        Response response = route.response(request);
        assertFalse(response.isEmpty());
    }
    
    @Test
    public void responseShouldBeEmptyWhenMethodsAreNotEqual() throws URISyntaxException{
        Route route = new MethodRoute(new Method(Method.POST), origin);
        Request request = new RouteRequest(new Method(Method.GET), new URI("http://localhost/test"));
        Response response = route.response(request);
        assertTrue(response.isEmpty());
    }
    
}
