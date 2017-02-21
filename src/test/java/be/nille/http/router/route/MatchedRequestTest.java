/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.request.Request;
import be.nille.http.router.response.Body;
import be.nille.http.router.response.DefaultResponse;
import be.nille.http.router.media.TextMedia;
import java.net.URISyntaxException;
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
public class MatchedRequestTest {

   
    

    @Test
    public void requestShouldHaveNoPathParams() throws URISyntaxException {
        DefaultRoute route = new DefaultRoute(new Method(Method.GET), new Path("/subscriber/search"),
               (request) -> new DefaultResponse(new Body(new TextMedia("content"))));

        Request request = new MatchedRequest(route,Request.builder()
                .withMethod(Method.GET)
                .withURI("http://localhost:8080/subscriber/search")
                .build());
        Map<String, String> pathParams = request.getPathParameters();

        assertTrue(pathParams.isEmpty());

    }

    @Test
    public void requestShouldHaveTwoPathParams() throws URISyntaxException {
        DefaultRoute route = new DefaultRoute(new Method(Method.GET), new Path("/subscriber/:subscriberId/search/:language"),
                (request) -> new DefaultResponse(new Body(new TextMedia("content"))));

        Request request =new MatchedRequest(route, Request.builder()
                .withMethod(Method.GET)
                .withURI("http://localhost:8080/subscriber/1/search/nl")
                
                .build());
        Map<String, String> pathParams = request.getPathParameters();

        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            log.debug(entry.getKey() + ":" + entry.getValue());
        }
        assertEquals(pathParams.get("language"), "nl");
        assertEquals(pathParams.get("subscriberId"), "1");

    }

    @Test
    public void requestShouldHaveOnePathParam() throws URISyntaxException {
        DefaultRoute route = new DefaultRoute(new Method(Method.GET), new Path("/subscriber/:subscriberId/search"),
                (request) -> new DefaultResponse(new Body(new TextMedia("content"))));

        Request request = new MatchedRequest(route,Request.builder()
                .withMethod(Method.GET)
                .withURI("http://localhost:8080/subscriber/1/search")
                
                .build());
        Map<String, String> pathParams = request.getPathParameters();
        assertTrue(pathParams.size() == 1);
        assertEquals(pathParams.get("subscriberId"), "1");

    }

}
