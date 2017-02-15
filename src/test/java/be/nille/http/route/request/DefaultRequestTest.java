/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route.request;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import static junit.framework.Assert.assertTrue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class DefaultRequestTest {
    
    @Test
    public void shouldHaveRightAmountOfParameters() throws URISyntaxException{
        Request request = new ImmutableRequest(new URI("http://youhost.com/test?param1=abc&param2=def&param2=ghi"));
        Map<String,List<String>> paramMap= request.getQueryParameters();     
        assertTrue(paramMap.size() == 2);
    }
    
}
