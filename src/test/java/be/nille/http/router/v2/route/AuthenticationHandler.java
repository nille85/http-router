/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;

import be.nille.http.router.v2.request.Request;
import be.nille.http.router.v2.response.Response;
import be.nille.http.router.v2.response.RouterResponse;
import be.nille.http.router.v2.route.RegexRoute;
import be.nille.http.router.v2.route.RouteHandler;

/**
 *
 * @author Niels Holvoet
 */
public class AuthenticationHandler implements RouteHandler{
    
    private final RegexRoute regex;
    private final RouteHandler origin;
    
    public AuthenticationHandler(final RegexRoute regex, final RouteHandler handler){
        this.regex = regex;
        this.origin = handler;
    }


    @Override
    public Response execute(Request request) {
       final String password = request.queryParameters().first("password").orElse("");
        final String user = request.queryParameters().first("username").orElse("");
        if("john".equals(user) && "passwd".equals(password)){
            return origin.execute(request);
        }
        return new RouterResponse().response(401);
    }

    @Override
    public boolean matches(Request request) {
        return regex.matches(request);
    }
    
}
