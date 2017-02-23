/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;

import be.nille.http.router.request.Request;
import be.nille.http.router.route.Method;
import be.nille.http.router.route.Regex;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class RegexTest {
    
    @Test
    public void regexShouldMatchRequestWhenAllRequestsShouldMatch(){
        Regex regex = new Regex("/(.*)");
        Request request = Request.builder()
                .withMethod(Method.GET)
                .withURI("http://localhost:8080/subscriptions")
                .build();
        assertTrue(regex.matches(request));
    }
    
    @Test
    public void regexShouldMatchRequestWhenBeginIsTheSame(){
        Regex regex = new Regex("/subsc(.*)");
        Request request = Request.builder()
                .withMethod(Method.GET)
                .withURI("http://localhost:8080/subscriptions")
                .build();
        assertTrue(regex.matches(request));
    }
    
}
