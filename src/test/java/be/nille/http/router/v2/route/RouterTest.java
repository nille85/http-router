/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;

import be.nille.http.router.v2.request.Method;
import be.nille.http.router.media.TextMedia;
import be.nille.http.router.v2.request.Request;
import be.nille.http.router.v2.response.RouterResponse;
import be.nille.http.router.v2.request.RouterRequest;
import be.nille.http.router.v2.response.Response;
import be.nille.http.router.v2.response.StatusCodeException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 *
 * @author Niels Holvoet
 */
@Slf4j
public class RouterTest {

    @Test
    public void testApi() throws URISyntaxException, StatusCodeException {
        Router router = new Router()
                .addHandler(
                        new RegexAndMethodMatchCallback(new MethodRoute(new Method(Method.GET)), new RegexRoute("(/subsc.*)/(.*)"))
                )
                .addHandler(new AllwaysMatchCallback());

        RouterRequest request = new RouterRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscribers/5?bla=test&bla=test2"),
                new TextMedia("hello"),
                new HashMap<>()
        );
        
        Response response = router.response(request);
    }
    
    @Test
    public void testAnoterOne() throws URISyntaxException, StatusCodeException {
        Router router = new Router().addHandler(
                        new PathWithParamsCallback(new ParameterizedPathRoute("/subscribers/:subscriberId"))
                )
                .addHandler(new AllwaysMatchCallback());

        RouterRequest request = new RouterRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscribers/5?bla=test&bla=test2"),
                new TextMedia("hello"),
                new HashMap<>()
        );
        router.response(request);
    }
    
    @Test
    public void testAuthentication() throws URISyntaxException, StatusCodeException {
        Router router = new Router().addHandler(
                        new AuthenticationHandler(new RegexRoute("/subscribers.*"),
                                new PathWithParamsCallback(new ParameterizedPathRoute("/subscribers/:subscriberId"))
                        )
                        
                )
                .addHandler(new AllwaysMatchCallback());

        RouterRequest request = new RouterRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscribers/5?username=joh&password=passwd"),
                new TextMedia("hello"),
                new HashMap<>()
        );
        Response response = router.response(request);
        log.debug(response.getBody().print() + ":" + response.getStatusCode().getValue());
    }
    
    
    
    
    private static class PathWithParamsCallback implements RouteHandler {

        
        private final ParameterizedPathRoute path;

        public PathWithParamsCallback( final ParameterizedPathRoute path) {
          
            this.path = path;
        }

        @Override
        public Response execute(Request request) {

            Map<String,String> map = path.getPathParameters(request);
            log.debug("params size:" + map.size());
            request
                    .queryParameters()
                    .list("bla")
                    .stream()
                    .forEach(v -> log.debug(v));
            return new RouterResponse().response(new TextMedia("hello world, authenticated"));
        }

        @Override
        public boolean matches(Request request) {

            return path.matches(request);
        }

    }

    private static class RegexAndMethodMatchCallback implements RouteHandler {

        private final MethodRoute methodRoute;
        private final RegexRoute regex;

        public RegexAndMethodMatchCallback(final MethodRoute methodRoute, final RegexRoute regex) {
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

    private static class AllwaysMatchCallback implements RouteHandler {

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
