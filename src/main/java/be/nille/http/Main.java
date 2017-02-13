/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http;

import be.nille.http.route.Route;
import be.nille.http.route.RouteRegistry;
import be.nille.http.route.SimpleResponse;

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
        
        
        

        RouteRegistry registry = new RouteRegistry()
                .withRoute(new Route("/subscriptions/{subscriptionId}","GET",(request) -> new SimpleResponse("this is the response","text/plain; charset=UTF-8"))
                )
                .withRoute(new Route("/subscriptions","POST",(request) -> new SimpleResponse("another response","text/plain; charset=UTF-8"))
                );
        
        new DefaultHttpServer(port, registry).run();
    }
    
}
