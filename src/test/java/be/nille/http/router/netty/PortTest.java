/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;


import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Niels Holvoet
 */
public class PortTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPortIsLessThanZero(){
        Port port = new Port(-1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPortIsEqualToZero(){
        Port port = new Port(0);
    }
    
    @Test
    public void shouldNotThrowExceptionWhenPortIsBiggerThanZero(){
        Port port = new Port(80);
        assertEquals(80, port.getValue());
    }
    
}
