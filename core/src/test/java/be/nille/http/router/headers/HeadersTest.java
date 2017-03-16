/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.headers;

import java.util.HashMap;
import java.util.Map;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Niels Holvoet
 */
public class HeadersTest {
    
    @Test
    public void mapShouldBeEmptyWhenNoHeadersWereAdded(){
        Headers headers = new Headers();
        assertTrue(headers.map().isEmpty());
    }
    
    @Test
    public void mapSizeShouldBeEqualToOriginalMapSize(){
        Map<String,String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        Headers headers = new Headers(map);
        assertTrue(headers.map().size() == map.size());
    }
    
    @Test
    public void addingAHeadersShouldIncreaseSize(){
        Map<String,String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        Headers headers = new Headers(map)
                .add("key4", "value4");
        
        assertTrue(headers.map().size() == 4);
    }
    
    @Test
    public void removingAHeadersShouldDecreaseSize(){
        Map<String,String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        Headers headers = new Headers(map)
                .remove("key2");
        
        assertTrue(headers.map().size() == 2);
    }
    
}
