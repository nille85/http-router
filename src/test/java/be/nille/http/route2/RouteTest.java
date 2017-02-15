/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route2;

import be.nille.http.route.request.ImmutableRequest;
import be.nille.http.route.request.Request;
import be.nille.http.route.response.Response;
import be.nille.http.route.response.ResponseBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Niels Holvoet
 */
public class RouteTest {
    
    @Test
    public void pathShouldMatchWhenSameAsInURL() throws URISyntaxException{
        Route route = new Route(new Method(Method.GET),new Path("/subscriber/1/search"), 
                (request) -> new Response(new Response.Body("content")));
        
        Request request = new ImmutableRequest(new URI("http://localhost:8080/subscriber/1/search"));
        
        assertTrue(route.matchesResource(request));
    }
    
    /*
    @Test
    public void pathShouldMatchWhenSameAsInURLWithParameters(){
        Path path = new Path("/subscriber/1/search");
        String pathString = "/subscriber/1/search?hello=p";
        assertTrue(path.matches(pathString));
    }
    
    @Test
    public void pathShouldMatchWhenSameAsInURLWithPathParam(){
        Path path = new Path("/subscriber/{subscriberId}/search");
        String pathString = "/subscriber/1/search";
        assertTrue(path.matches(pathString));
    }
    
    @Test
    public void pathShouldNotMatchWhenDifferentAsInUrl(){
        Path path = new Path("/subscriber/1/search");    
        String pathString = "/subscription/2/search";
        assertFalse(path.matches(pathString));
    }
    
    @Test
    public void pathShouldNotMatchWhenDifferentStart(){
        Path path = new Path("/subscriber/1/search");    
        String pathString = "subscriber/1/search";
        assertFalse(path.matches(pathString));
    }
    
    
    @Test
    public void pathShouldNotMatchWhenDifferentEnd(){
        Path path = new Path("/subscriber/1/search");    
        String pathString = "/subscriber/1/searchp";
        assertFalse(path.matches(pathString));
    }
    */
    
}
