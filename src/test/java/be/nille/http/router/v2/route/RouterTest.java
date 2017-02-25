/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;

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
        Router router = new Router().
                addCallback(
                        new RegexAndMethodMatchCallback(new Method(Method.GET), new Regex("(/subsc.*)/(.*)"))
                )
                .addCallback(new AllwaysMatchCallback());

        RouterRequest request = new RouterRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscribers/5?bla=test&bla=test2"),
                new TextMedia("hello"),
                new HashMap<>()
        );
        router.evaluate(request);
    }
    
    @Test
    public void testAnoterOne() throws URISyntaxException, StatusCodeException {
        Router router = new Router().
                addCallback(
                        new PathWithParamsCallback(new Method(Method.GET), new PathWithParams("/subscribers/:subscriberId"))
                )
                .addCallback(new AllwaysMatchCallback());

        RouterRequest request = new RouterRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscribers/5?bla=test&bla=test2"),
                new TextMedia("hello"),
                new HashMap<>()
        );
        router.evaluate(request);
    }
    
    
    private static class PathWithParamsCallback implements RouteCallback {

        private final Method method;
        private final PathWithParams path;

        public PathWithParamsCallback(final Method method, final PathWithParams path) {
            this.method = method;
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
            return new RouterResponse();
        }

        @Override
        public boolean matches(Request request) {

            return method.matches(request) && path.matches(request);
        }

    }

    private static class RegexAndMethodMatchCallback implements RouteCallback {

        private final Method method;
        private final Regex regex;

        public RegexAndMethodMatchCallback(final Method method, final Regex regex) {
            this.method = method;
            this.regex = regex;
        }

        @Override
        public Response execute(Request request) {

            regex.variables(request).stream().forEach(v -> log.debug(v));

            request
                    .queryParameters()
                    .list("bla")
                    .stream()
                    .forEach(v -> log.debug(v));
            return new RouterResponse();
        }

        @Override
        public boolean matches(Request request) {

            return method.matches(request) && regex.matches(request);
        }

    }

    private static class AllwaysMatchCallback implements RouteCallback {

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
