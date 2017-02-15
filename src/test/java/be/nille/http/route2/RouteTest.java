/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route2;

import be.nille.http.route.request.ImmutableRequest;
import be.nille.http.route.request.Request;
import be.nille.http.route.response.DefaultResponse;
import be.nille.http.route.response.Response;
import be.nille.http.route.response.ResponseBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Niels Holvoet
 */
@Slf4j
public class RouteTest {

    @Test
    public void pathShouldMatchWhenSameAsInURL() throws URISyntaxException {
        Route route = new Route(new Method(Method.GET), new Path("/subscriber/1/search"),
                (request) -> new DefaultResponse(new Response.Body("content")));

        Request request = new ImmutableRequest(new URI("http://localhost:8080/subscriber/1/search"));

        assertTrue(route.matchesResource(request));
    }

    @Test
    public void pathShouldMatchWhenSameAsInURLWithParameters() throws URISyntaxException {

        Route route = new Route(new Method(Method.GET), new Path("/subscriber/1/search"),
                (request) -> new DefaultResponse(new Response.Body("content")));

        Request request = new ImmutableRequest(new URI("http://localhost:8080/subscriber/1/search?hello=p"));

        assertTrue(route.matchesResource(request));
    }

    @Test
    public void pathShouldMatchWhenSameAsInURLWithPathParam() throws URISyntaxException {
        Route route = new Route(new Method(Method.GET), new Path("/subscriber/:subscriberId/search"),
                (request) -> new DefaultResponse(new Response.Body("content")));

        Request request = new ImmutableRequest(new URI("http://localhost:8080/subscriber/1/search"));

        assertTrue(route.matchesResource(request));

    }
    
    @Test
    public void pathShouldMatchWhenSameAsInURLWithPathParamAtEnd() throws URISyntaxException {
        Route route = new Route(new Method(Method.GET), new Path("/subscriber/search/:subscriberId"),
                (request) -> new DefaultResponse(new Response.Body("content")));

        Request request = new ImmutableRequest(new URI("http://localhost:8080/subscriber/search/1"));

        assertTrue(route.matchesResource(request));

    }

    @Test
    public void pathShouldNotMatchWhenDifferentAsInUrl() throws URISyntaxException {

        Route route = new Route(new Method(Method.GET), new Path("/subscription/2/search"),
                (request) -> new DefaultResponse(new Response.Body("content")));

        Request request = new ImmutableRequest(new URI("http://localhost:8080/subscriber/1/search"));

        assertFalse(route.matchesResource(request));
    }

    @Test
    public void pathShouldNotMatchWhenDifferentStart() throws URISyntaxException {

        Route route = new Route(new Method(Method.GET), new Path("subscription/1/search"),
                (request) -> new DefaultResponse(new Response.Body("content")));

        Request request = new ImmutableRequest(new URI("http://localhost:8080/subscriber/1/search"));

        assertFalse(route.matchesResource(request));
    }

    @Test
    public void pathShouldNotMatchWhenDifferentEnd() throws URISyntaxException {

        Route route = new Route(new Method(Method.GET), new Path("subscription/1/searchp"),
                (request) -> new DefaultResponse(new Response.Body("content")));

        Request request = new ImmutableRequest(new URI("http://localhost:8080/subscriber/1/search"));

        assertFalse(route.matchesResource(request));
    }
    
    
    @Test
    public void routeShouldHaveNoPathParams() throws URISyntaxException {
        Route route = new Route(new Method(Method.GET), new Path("/subscriber/search"),
                (request) -> new DefaultResponse(new Response.Body("content")));

        Request request = new ImmutableRequest(new URI("http://localhost:8080/subscriber/search"));

        Map<String,String> pathParams = route.getPathVariables(request);
        
        assertTrue(pathParams.isEmpty());
      

    }
    
    @Test
    public void routeShouldHaveTwoPathParams() throws URISyntaxException {
        Route route = new Route(new Method(Method.GET), new Path("/subscriber/:subscriberId/search/:language"),
                (request) -> new DefaultResponse(new Response.Body("content")));

        Request request = new ImmutableRequest(new URI("http://localhost:8080/subscriber/1/search/nl"));

        Map<String,String> pathParams = route.getPathVariables(request);
        for(Map.Entry<String,String> entry : pathParams.entrySet()){
            log.debug(entry.getKey() + ":" + entry.getValue());
        } 
        assertEquals(pathParams.get("language"),"nl");
        assertEquals(pathParams.get("subscriberId"),"1");

    }
    
    @Test
    public void routeShouldHaveOnePathParam() throws URISyntaxException {
        Route route = new Route(new Method(Method.GET), new Path("/subscriber/:subscriberId/search"),
                (request) -> new DefaultResponse(new Response.Body("content")));

        Request request = new ImmutableRequest(new URI("http://localhost:8080/subscriber/1/search"));

        Map<String,String> pathParams = route.getPathVariables(request);
       
        assertTrue(pathParams.size() == 1);
        assertEquals(pathParams.get("subscriberId"),"1");
       

    }

}
