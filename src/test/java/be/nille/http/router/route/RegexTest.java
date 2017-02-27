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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class RegexTest {
    
    @Test
    public void regexShouldMatchRequestWhenAllRequestsShouldMatch() throws URISyntaxException{
        RegexRoute regex = new RegexRoute("/(.*)");
       
        
       Request request = new RouteRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscriptions"),
                new TextBody("hello"),
                new Headers()
        );
        
        assertTrue(regex.matches(request));
    }
    
    @Test
    public void regexShouldMatchRequestWhenBeginIsTheSame() throws URISyntaxException{
        RegexRoute regex = new RegexRoute("/subsc(.*)");
        Request request = new RouteRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscriptions"),
                new TextBody("hello"),
                new Headers()
        );
        assertTrue(regex.matches(request));
    }
    
}
