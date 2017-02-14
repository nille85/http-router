/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http;

import be.nille.http.route2.Route;
import be.nille.http.route2.RouteRegistry;
import be.nille.http.route.response.DefaultResponse;
import be.nille.http.route.response.Response;

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
                .withRoute(new Route("GET","/subscriptions/{subscriptionId}",(request) -> new DefaultResponse(new Response.Body("sdmlkfsd"))
                ))
                .withRoute(new Route("POST","/subscriptions",(request) -> new DefaultResponse(new Response.Body("sdmlkfsd")))
                );
        
        new DefaultHttpServer(port, registry).run();
    }
    
}
