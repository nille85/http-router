/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;



import be.nille.http.router.media.TextMedia;
import be.nille.http.router.v2.request.Request;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class PathTest {
    
    
    @Test
    public void routeShouldMatchRequestWhenPathIsTheSame() throws URISyntaxException{
        EqualPath path = new EqualPath("/subscriptions");
       
        Request request = new Request(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscriptions"),
                new TextMedia("hello"),
                new HashMap<>()
        );
        assertTrue(path.matches(request));
    }
    
    
    @Test
    public void routeShouldNotMatchRequestWhenPathIsDifferent() throws URISyntaxException{
        EqualPath path = new EqualPath("/subscriptions");
        
        
        Request request = new Request(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscribers"),
                new TextMedia("hello"),
                new HashMap<>()
        );
        
        assertFalse(path.matches(request));
    }
    
    @Test
    public void routeShouldMatchRequestWhenRequestPathContainsParameters() throws URISyntaxException{
        EqualPath path = new EqualPath("/subscriptions");   
         Request request = new Request(
                new Method(Method.GET),
                new URI("http://localhost:8080/subscriptions?param1=something&param2=somethingelse"),
                new TextMedia("hello"),
                new HashMap<>()
        );
        assertTrue(path.matches(request));
    }
    
    
}
