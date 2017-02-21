/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.request;

import be.nille.http.router.request.Request;
import be.nille.http.router.response.DefaultResponse;
import be.nille.http.router.response.Response;
import be.nille.http.router.route.MatchedRequest;
import be.nille.http.router.route.Method;
import be.nille.http.router.route.Path;
import be.nille.http.router.route.DefaultRoute;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class RequestTest {

    @Test
    public void shouldHaveRightAmountOfParameters()  {
        
        Request request = Request.builder()
                .withURI("http://youhost.com/test?param1=abc&param2=def&param2=ghi")
                .withMethod(Method.GET)
                .build();

        Map<String, List<String>> paramMap = request.getQueryParameters();
        assertTrue(paramMap.size() == 2);
    }


}
