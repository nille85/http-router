/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http;

import be.nille.http.route.HttpRouter;
import be.nille.http.route2.RouteBuilder;
import be.nille.http.route2.Route;
import be.nille.http.route.response.Response;
import be.nille.http.route.response.ResponseBuilder;
import be.nille.http.route2.Method;

/**
 *
 * @author Niels Holvoet
 */
public class Main {
    
     public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        
       
        HttpRouter router = new HttpRouter()
                .listenTo(port);
        
        
        
        router.withRoutes()           
                    .add(Route.builder()
                            .withMethod(Method.POST)
                            .withPath("/subscriptions")
                            .withHandler(
                                (request) -> Response.builder().withBody("this is my content").build())
                            .build()
                    ).save();
        
        
        router.withRoutes()
                    .add(new RouteBuilder()
                            .withHandler((request) -> new Response(new Response.Body("sdmlkfsd")))
                            .withMethod(Method.GET)
                            .withPath("/subscriptions/{subscriptionId}")
                            .build()
                    )
                    .save();      
        
        router.start();
    }
    
}
