/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.route.EqualPathRoute;
import be.nille.http.router.request.Method;
import be.nille.http.router.body.TextBody;
import be.nille.http.router.headers.Headers;
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
 * @author nholvoet
 */
public class PathRouteTest {

    private Route origin;

    @Before
    public void setup() {
        origin = (request) -> new RouteResponse(new StatusCode(StatusCode.OK), new TextBody("Hello World"));
    }

    @Test
    public void routeShouldMatchRequestWhenPathIsTheSame() throws URISyntaxException {
        PathRoute route = new PathRoute("/subscriptions", origin);

        RouteRequest request = new RouteRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscriptions"),
                new TextBody("hello"),
                new Headers()
        );
        Response response = route.response(request);
        assertFalse(response.isEmpty());
        
    }

    @Test
    public void routeShouldNotMatchRequestWhenPathIsDifferent() throws URISyntaxException {
        PathRoute route = new PathRoute("/subscriptions", origin);

        RouteRequest request = new RouteRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscribers"),
                new TextBody("hello"),
                new Headers()
        );
        Response response = route.response(request);
        assertTrue(response.isEmpty());
    }

    @Test
    public void routeShouldMatchRequestWhenRequestPathContainsParameters() throws URISyntaxException {
        PathRoute route = new PathRoute("/subscriptions", origin);
        RouteRequest request = new RouteRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscriptions?param1=something&param2=somethingelse"),
                new TextBody("hello"),
                new Headers()
        );
        Response response = route.response(request);
        assertFalse(response.isEmpty());
    }

}
