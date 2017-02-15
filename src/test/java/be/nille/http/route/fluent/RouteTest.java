/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route.fluent;

import be.nille.http.route2.RouteBuilder;
import be.nille.http.route.request.Request;
import be.nille.http.route.response.Response;
import be.nille.http.route.response.ResponseBuilder;
import be.nille.http.route2.Route;
import be.nille.http.route2.Method;
import be.nille.http.route2.Path;
import be.nille.http.route2.RequestHandler;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class RouteTest {
    
    @Test
    public void saveShouldNotThrowException(){
        Route route = new RouteBuilder()
                .withPath("/subscription/search")
                .withMethod(Method.GET)
                .withHandler(new MyRequestHandler())
                .build();
    }
    
    private static class MyRequestHandler implements RequestHandler{

        @Override
        public Response handle(Request request) {
            return new Response(new Response.Body("this is a simple response"));
        }
        
    }
    
}
