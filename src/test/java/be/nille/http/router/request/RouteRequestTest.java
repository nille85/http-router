/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.request;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import static junit.framework.Assert.assertFalse;
import org.junit.Test;

/**
 *
 * @author Niels Holvoet
 */
public class RouteRequestTest {
    
    @Test
    public void shouldCreateCopyOfRequestWithVariables() throws URISyntaxException{
        Request request = new RouteRequest(new Method(Method.GET), new URI("http://localhost/test"));
        Map<String,String> variables = new HashMap<>();
        variables.put("variables", "value");
        Request copy = new RouteRequest(request, new PathVariables(variables));
        assertFalse(copy.variables().map().isEmpty());
    }
    
}
