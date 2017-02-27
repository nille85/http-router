/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.route.RegexRoute;
import be.nille.http.router.request.Method;
import be.nille.http.router.body.TextBody;
import be.nille.http.router.headers.Headers;
import be.nille.http.router.request.Request;
import be.nille.http.router.request.RouteRequest;
import be.nille.http.router.response.Response;
import be.nille.http.router.response.RouteResponse;
import be.nille.http.router.response.StatusCode;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class RegexRouteTest {

    @Test
    public void testRegexGroups() {
        Pattern pattern = Pattern.compile("^/test/(.*)$");
        Matcher matcher = pattern.matcher("/test/subscriptions");
 
        while (matcher.find()) {
            System.out.format("Text \"%s\" found at %d to %d.%n",
                    matcher.group(1), matcher.start(), matcher.end());
        }
        
        
    }

    @Test
    public void regexShouldMatchRequestWhenAllRequestsShouldMatch() throws URISyntaxException {
        RegexRoute regex = new RegexRoute(
                "^/(.*)$",
                (request) -> new RouteResponse(
                        new StatusCode(StatusCode.OK),
                        new TextBody(request.variables().get("1"))
                )
        );

        Request request = new RouteRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscriptions"),
                new TextBody("hello"),
                new Headers()
        );

        Response response = regex.response(request);
        log.debug(response.getBody().print());
        assertEquals("subscriptions", response.getBody().print());
    }
    
    @Test
    public void regexShouldMatchRequestWhenNoGroupsInRegex() throws URISyntaxException {
        RegexRoute regex = new RegexRoute(
                "^/.*$",
                (request) -> new RouteResponse(
                        new StatusCode(StatusCode.OK),
                        new TextBody("")
                )
        );

        Request request = new RouteRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscriptions"),
                new TextBody("hello"),
                new Headers()
        );

        Response response = regex.response(request);
        log.debug(response.getBody().print());
        assertEquals("", response.getBody().print());
    }

    @Test
    public void regexShouldMatchRequestWhenBeginIsTheSame() throws URISyntaxException {
        RegexRoute regex = new RegexRoute(
                "^/subsc(.*)$",
                (request) -> new RouteResponse(
                        new StatusCode(StatusCode.OK),
                        new TextBody(request.variables().get("1"))
                )
        );
        Request request = new RouteRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscriptions"),
                new TextBody("hello"),
                new Headers()
        );
        Response response = regex.response(request);
        log.debug(response.getBody().print());
        assertEquals("riptions", response.getBody().print());
    }

}
