/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route2;

import be.nille.http.route.request.DefaultRequest;
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

       
        assertTrue(route.matchesResource("/subscriber/1/search"));
    }

    @Test
    public void pathShouldMatchWhenSameAsInURLWithParameters() throws URISyntaxException {

        Route route = new Route(new Method(Method.GET), new Path("/subscriber/1/search"),
                (request) -> new DefaultResponse(new Response.Body("content")));



        assertTrue(route.matchesResource("/subscriber/1/search?hello=p"));
    }

    @Test
    public void pathShouldMatchWhenSameAsInURLWithPathParam() throws URISyntaxException {
        Route route = new Route(new Method(Method.GET), new Path("/subscriber/:subscriberId/search"),
                (request) -> new DefaultResponse(new Response.Body("content")));

       

        assertTrue(route.matchesResource("/subscriber/1/search"));

    }
    
    @Test
    public void pathShouldMatchWhenSameAsInURLWithPathParamAtEnd() throws URISyntaxException {
        Route route = new Route(new Method(Method.GET), new Path("/subscriber/search/:subscriberId"),
                (request) -> new DefaultResponse(new Response.Body("content")));

     

        assertTrue(route.matchesResource("/subscriber/search/1"));

    }

    @Test
    public void pathShouldNotMatchWhenDifferentAsInUrl() throws URISyntaxException {

        Route route = new Route(new Method(Method.GET), new Path("/subscription/2/search"),
                (request) -> new DefaultResponse(new Response.Body("content")));


        assertFalse(route.matchesResource("/subscriber/1/search"));
    }

    @Test
    public void pathShouldNotMatchWhenDifferentStart() throws URISyntaxException {

        Route route = new Route(new Method(Method.GET), new Path("subscription/1/search"),
                (request) -> new DefaultResponse(new Response.Body("content")));

       

        assertFalse(route.matchesResource("/subscriber/1/search"));
    }

    @Test
    public void pathShouldNotMatchWhenDifferentEnd() throws URISyntaxException {

        Route route = new Route(new Method(Method.GET), new Path("subscription/1/searchp"),
                (request) -> new DefaultResponse(new Response.Body("content")));

      

        assertFalse(route.matchesResource("/subscriber/1/search"));
    }
    
    
    
}
