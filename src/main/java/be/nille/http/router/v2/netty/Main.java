/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.netty;

import be.nille.http.router.v2.request.Request;
import be.nille.http.router.v2.response.Response;
import be.nille.http.router.v2.response.RouterResponse;
import be.nille.http.router.v2.request.Method;
import be.nille.http.router.v2.route.MethodRoute;
import be.nille.http.router.v2.route.RegexRoute;
import be.nille.http.router.v2.route.RouteHandler;
import be.nille.http.router.v2.route.Router;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Niels Holvoet
 */
@Slf4j
public class Main {
    
    public static void main(final String[] args) throws Exception{
        Router router = new Router().addHandler(
                new SubscriptionHandler(new MethodRoute(new Method(Method.GET)), new RegexRoute("(/subsc.*)/(.*)"))
                )
                .addHandler(new FallbackHandler());
        
        new NettyHttpServer(router,80).start();
    }
    
    private static class SubscriptionHandler implements RouteHandler {

        private final MethodRoute methodRoute;
        private final RegexRoute regex;

        public SubscriptionHandler(final MethodRoute methodRoute, final RegexRoute regex) {
            this.methodRoute = methodRoute;
            this.regex = regex;
        }

        @Override
        public Response execute(Request request) {

            regex.getVariables(request).stream().forEach(v -> log.debug(v));

            request
                    .queryParameters()
                    .list("bla")
                    .stream()
                    .forEach(v -> log.debug(v));
            return new RouterResponse();
        }

        @Override
        public boolean matches(Request request) {
           
            return methodRoute.matches(request) && regex.matches(request);
        }

    }

    private static class FallbackHandler implements RouteHandler {

        @Override
        public Response execute(Request request) {
            request
                    .queryParameters()
                    .list("bla")
                    .stream()
                    .forEach(v -> log.debug(v));
            return new RouterResponse();
        }

        @Override
        public boolean matches(Request request) {
            return true;
        }

    }
    
}
