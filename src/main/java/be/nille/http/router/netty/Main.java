/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import be.nille.http.router.body.TextBody;
import be.nille.http.router.request.Request;
import be.nille.http.router.response.Response;
import be.nille.http.router.response.RouteResponse;
import be.nille.http.router.request.Method;
import be.nille.http.router.response.StatusCode;
import be.nille.http.router.route.MethodRoute;
import be.nille.http.router.route.RegexRoute;
import be.nille.http.router.route.RouteHandler;
import be.nille.http.router.route.ListRoute;
import be.nille.http.router.route.Route;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Niels Holvoet
 */
@Slf4j
public class Main {
    
    public static void main(final String[] args) throws Exception{
        
        List<Route> routes = new ArrayList<>();
        Route getRoute = new MethodRoute(
                new Method(Method.GET),
                (request) -> new RouteResponse(new StatusCode(StatusCode.OK), new TextBody("Hello GET route"))
        );
        routes.add(getRoute);

        Route postRoute = new MethodRoute(
                new Method(Method.POST),
                (request) -> new RouteResponse(new StatusCode(StatusCode.OK), new TextBody("Hello POST route"))
        );
        routes.add(postRoute);
        
        Route deleteRoute = new MethodRoute(
                new Method(Method.DELETE),
                (request) -> new RouteResponse(new StatusCode(StatusCode.OK), new TextBody("Hello DELETE route"))
        );
        routes.add(deleteRoute);
        
        Route listRoute = new ListRoute(routes);
        
        new HttpRouter(listRoute).start();
    }
    
    private static class SubscriptionRoute implements Route {


        @Override
        public Response response(Request request) {

            request
                    .queryParameters()
                    .list("bla")
                    .stream()
                    .forEach(v -> log.debug(v));
            return new RouteResponse();
        }

    }

    private static class FallbackRoute implements Route {

        @Override
        public Response response(Request request) {
            request
                    .queryParameters()
                    .list("bla")
                    .stream()
                    .forEach(v -> log.debug(v));
            return new RouteResponse();
        }

    }
    
}
