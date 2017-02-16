/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router;

import be.nille.http.router.response.Response;
import be.nille.http.router.route.Method;
import be.nille.http.router.route.Route;
import be.nille.http.router.netty.HttpServer;
import be.nille.http.router.route.Path;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author nholvoet
 */
public class HttpRouterTest {
    
    
    private HttpServer httpServer; 
 
    private HttpRouter router;
    
    @Before
    public void setup() throws Exception{
        httpServer = Mockito.mock(HttpServer.class);
        router = new HttpRouter(httpServer);
       
    }
    
    @Test
    public void routerShouldAddRoutesCorrectlyToRegistry() throws Exception{
        
        router.addRoute(new Route(
                Method.GET,
                "/subscriptions",
                (request) -> Response.builder().build()
        ));
        router.addRoute(new Route(
                Method.POST,
                "/subscriptions",
                (request) -> Response.builder().build()
        ));
        
        assertEquals(2,router.getRoutes().size());
    }
    
    
    @Test(expected = HttpRouterException.class)
    public void routerShouldThrowExceptionWhenStartedWithoutRoutes() throws Exception{
       router.start();
    }
    
    @Test
    public void routerShouldStartWhenRoutesAreDefined() throws Exception{
         Mockito.doNothing().when(httpServer).run(any(RouteRegistry.class));
        router.addRoute(new Route(
                Method.GET,
                "/subscriptions",
                (request) -> Response.builder().build()
        ));
        
        router.start();
      
    }
    
    @Test(expected = HttpRouterException.class)
    public void routerShouldThrowExceptionWhenServerThrowsException() throws Exception{
        Mockito.doThrow(new RuntimeException("Http server exception")).when(httpServer).run(any(RouteRegistry.class));
        router.addRoute(new Route(
                Method.GET,
                "/subscriptions",
                (request) -> Response.builder().build()
        ));
        
        router.start();
      
    }
    
    
    
    
    
    
}
