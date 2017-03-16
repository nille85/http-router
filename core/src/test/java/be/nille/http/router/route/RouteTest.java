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
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Niels Holvoet
 */
public class RouteTest {
    
    @Test
    public void routedResponseShouldBeEqualToTheOriginalResponse() throws URISyntaxException{
       
        Response routeResponse = new RouteResponse(new StatusCode(StatusCode.OK), new TextBody("Hello World"))
                .withHeader("content-type", "application/json");
        Route route = (request) -> routeResponse;
        Request request = new RouteRequest(new Method(Method.GET), new URI("http://localhost/test"));
        Response response = route.response(request);
        assertEquals(response, routeResponse);
    }
    
}
