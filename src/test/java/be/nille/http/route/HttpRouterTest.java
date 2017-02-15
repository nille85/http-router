/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

import static junit.framework.Assert.assertFalse;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class HttpRouterTest {
    
    @Test
    public void routerShouldNotStartServerWhenCreated(){
        HttpRouter router = new HttpRouter();
        assertFalse(router.isStarted());
    }
    
}
