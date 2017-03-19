package be.nille.http.router.route;

import be.nille.http.router.body.TextBody;
import be.nille.http.router.request.Method;
import be.nille.http.router.request.Request;
import be.nille.http.router.request.RouteRequest;
import be.nille.http.router.response.Response;
import be.nille.http.router.response.RouteResponse;
import be.nille.http.router.response.StatusCode;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Niels on 19/03/2017.
 */
public class GetRouteTest {

    private Route origin;

    @Before
    public void setup(){
        origin = (request) -> new RouteResponse(new StatusCode(StatusCode.OK), new TextBody("Hello World"));
    }


    @Test
    public void responseShouldBeEqualWhenGetRequestWasSent() throws URISyntaxException {
        Route route = new GetRoute(new PathRoute("/hello", origin));
        Request request = new RouteRequest(new Method(Method.GET), new URI("http://localhost/hello"));
        Response response = route.response(request);
        assertArrayEquals("Hello World".getBytes(), response.getBody().bytes());
    }

    @Test
    public void responseShouldBeEmptyWhenPostRequestWasSent() throws URISyntaxException {
        Route route = new GetRoute(new PathRoute("/hello", origin));
        Request request = new RouteRequest(new Method(Method.POST), new URI("http://localhost/hello"));
        Response response = route.response(request);
        assertTrue(response.isEmpty());
    }

}
