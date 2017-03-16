/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.body.TextBody;
import be.nille.http.router.request.Method;
import be.nille.http.router.request.Request;
import be.nille.http.router.request.RouteRequest;
import be.nille.http.router.response.Response;
import be.nille.http.router.response.RouteResponse;
import be.nille.http.router.response.StatusCode;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Niels Holvoet
 */
public class ListRouteTest {

    private Route listRoute;

    @Before
    public void setup() {

        List<Route> routes = new ArrayList<>();
        Route route1 = new MethodRoute(
                new Method(Method.GET),
                (request) -> new RouteResponse(new StatusCode(StatusCode.OK), new TextBody("Hello route 1"))
        );
        routes.add(route1);

        Route route2 = new MethodRoute(
                new Method(Method.POST),
                (request) -> new RouteResponse(new StatusCode(StatusCode.OK), new TextBody("Hello route 2"))
        );

        routes.add(route2);
        Route route3 = new MethodRoute(
                new Method(Method.DELETE),
                (request) -> new RouteResponse(new StatusCode(StatusCode.OK), new TextBody("Hello route 3"))
        );
        routes.add(route3);
        
        listRoute = new ListRoute(routes);
    }
    
    @Test
    public void listRouteShouldReturnGetRouteWhenGetRequestWasSubmitted() throws URISyntaxException{
        Request request = new RouteRequest(new Method(Method.GET), new URI("http://localhost/test"));
        Response response = listRoute.response(request);
        assertArrayEquals("Hello route 1".getBytes(),response.getBody().bytes());
    }
    
    @Test
    public void listRouteShouldReturnPostRouteWhenPostRequestWasSubmitted() throws URISyntaxException{
        Request request = new RouteRequest(new Method(Method.POST), new URI("http://localhost/test"));
        Response response = listRoute.response(request);
        assertArrayEquals("Hello route 2".getBytes(),response.getBody().bytes());
    }
    
    @Test
    public void listRouteShouldReturnDeleteRouteWhenDeleteRequestWasSubmitted() throws URISyntaxException{
        Request request = new RouteRequest(new Method(Method.DELETE), new URI("http://localhost/test"));
        Response response = listRoute.response(request);
        assertArrayEquals("Hello route 3".getBytes(),response.getBody().bytes());
    }
    
    @Test
    public void listRouteShouldReturnEmptyResponseWhenPutRequestWasSubmitted() throws URISyntaxException{
        Request request = new RouteRequest(new Method(Method.PUT), new URI("http://localhost/test"));
        Response response = listRoute.response(request);
        assertTrue(response.isEmpty());
    }

}
