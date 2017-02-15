/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http;

import be.nille.http.route.HttpRouter;
import be.nille.http.route.response.Response;
import be.nille.http.route2.Method;
import be.nille.http.route2.Path;
import be.nille.http.route2.Route;

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
        
       
        
        router.addRoute(
                new Route(
                        new Method(Method.GET),
                        new Path("/subscriptions"),
                        (request) -> Response.builder().withBody("this is my content").build()
                )
        );
              
        
        
        router.start();
       
    }
    
}
