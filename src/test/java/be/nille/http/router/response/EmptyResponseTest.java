/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.response;

import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Niels Holvoet
 */
public class EmptyResponseTest {
    
    @Test
    public void responseShouldAlwaysBeEmpty(){
        assertTrue(new EmptyResponse().isEmpty());
    }
    
}
