/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.response.Body;
import be.nille.http.router.response.DefaultResponse;
import be.nille.http.router.media.TextMedia;
import java.net.URISyntaxException;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 *
 * @author Niels Holvoet
 */
@Slf4j
public class RouteTest {

    @Test
    public void pathShouldMatchWhenSameAsInURL() throws URISyntaxException {
        DefaultRoute route = new DefaultRoute(new Method(Method.GET), new Path("/subscriber/1/search"),
                (request) -> new DefaultResponse(new Body(new TextMedia("content"))));

       
        assertTrue(route.matchesResource("/subscriber/1/search"));
    }

    @Test
    public void pathShouldMatchWhenSameAsInURLWithParameters() throws URISyntaxException {

        DefaultRoute route = new DefaultRoute(new Method(Method.GET), new Path("/subscriber/1/search"),
                (request) -> new DefaultResponse(new Body(new TextMedia("content"))));



        assertTrue(route.matchesResource("/subscriber/1/search?hello=p"));
    }

    @Test
    public void pathShouldMatchWhenSameAsInURLWithPathParam() throws URISyntaxException {
        DefaultRoute route = new DefaultRoute(new Method(Method.GET), new Path("/subscriber/:subscriberId/search"),
              (request) -> new DefaultResponse(new Body(new TextMedia("content"))));

       

        assertTrue(route.matchesResource("/subscriber/1/search"));

    }
    
    @Test
    public void pathShouldMatchWhenSameAsInURLWithPathParamAtEnd() throws URISyntaxException {
        DefaultRoute route = new DefaultRoute(new Method(Method.GET), new Path("/subscriber/search/:subscriberId"),
               (request) -> new DefaultResponse(new Body(new TextMedia("content"))));

     

        assertTrue(route.matchesResource("/subscriber/search/1"));

    }

    @Test
    public void pathShouldNotMatchWhenDifferentAsInUrl() throws URISyntaxException {

        DefaultRoute route = new DefaultRoute(new Method(Method.GET), new Path("/subscription/2/search"),
                (request) -> new DefaultResponse(new Body(new TextMedia("content"))));


        assertFalse(route.matchesResource("/subscriber/1/search"));
    }

    @Test
    public void pathShouldNotMatchWhenDifferentStart() throws URISyntaxException {

        DefaultRoute route = new DefaultRoute(new Method(Method.GET), new Path("subscription/1/search"),
                (request) -> new DefaultResponse(new Body(new TextMedia("content"))));

       

        assertFalse(route.matchesResource("/subscription/1/search"));
    }

    @Test
    public void pathShouldNotMatchWhenDifferentEnd() throws URISyntaxException {

        DefaultRoute route = new DefaultRoute(new Method(Method.GET), new Path("subscription/1/searchp"),
               (request) -> new DefaultResponse(new Body(new TextMedia("content"))));

      

        assertFalse(route.matchesResource("/subscription/1/search"));
    }
    
    
    @Test
    public void test() throws URISyntaxException {

        DefaultRoute route = new DefaultRoute(new Method(Method.POST), new Path("/:personId/persons"),
                (request) -> new DefaultResponse(new Body(new TextMedia("content"))));

      

        assertFalse(route.matchesResource("/subscriptions"));
    }
    
    
    
}
