/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;

import be.nille.http.router.media.TextMedia;
import be.nille.http.router.v2.request.Request;
import be.nille.http.router.v2.request.RouterRequest;
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
        Regex regex = new Regex("/(.*)");
       
        
       Request request = new RouterRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscriptions"),
                new TextMedia("hello"),
                new HashMap<>()
        );
        
        assertTrue(regex.matches(request));
    }
    
    @Test
    public void regexShouldMatchRequestWhenBeginIsTheSame() throws URISyntaxException{
        Regex regex = new Regex("/subsc(.*)");
        Request request = new RouterRequest(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscriptions"),
                new TextMedia("hello"),
                new HashMap<>()
        );
        assertTrue(regex.matches(request));
    }
    
}
