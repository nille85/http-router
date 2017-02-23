/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;

import be.nille.http.router.request.Request;
import be.nille.http.router.route.Method;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class MethodTest {
    
     @Test
    public void methodShouldMatchRequestWhenMethodsAreTheSame(){
        Method method = new Method(Method.GET);
        Request request = Request.builder()
                .withMethod(Method.GET)
                .withURI("http://localhost:8080/subscribers")
                .build();
        assertTrue(method.matches(request));
    }
    
    @Test
    public void methodShouldNotMatchRequestWhenMethodsAreDifferent(){
        Method method = new Method(Method.GET);
        Request request = Request.builder()
                .withMethod(Method.POST)
                .withURI("http://localhost:8080/subscribers")
                .build();
        assertFalse(method.matches(request));
    }
    
}
