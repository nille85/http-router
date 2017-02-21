/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.request.Request;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class PathTest {
    
    
    @Test
    public void routeShouldMatchRequestWhenPathIsTheSame(){
        Path path = new Path("/subscriptions");
        Request request = Request.builder()
                .withMethod(Method.GET)
                .withURI("http://localhost:8080/subscriptions")
                .build();
        assertTrue(path.matches(request));
    }
    
    @Test
    public void routeShouldNotMatchRequestWhenPathIsDifferent(){
        Path path = new Path("/subscriptions");
        Request request = Request.builder()
                .withMethod(Method.GET)
                .withURI("http://localhost:8080/subscribers")
                .build();
        assertFalse(path.matches(request));
    }
    
    @Test
    public void routeShouldMatchRequestWhenRequestPathContainsParameters(){
        Path path = new Path("/subscriptions");
        Request request = Request.builder()
                .withMethod(Method.GET)
                .withURI("http://localhost:8080/subscriptions?param1=something&param2=somethingelse")
                .build();
        assertTrue(path.matches(request));
    }
    
}
