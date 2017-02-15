/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

import be.nille.http.ImmutableHttpServer;
import be.nille.http.HttpServer;
import be.nille.http.route.exception.DefaultRouteExceptionHandler;
import be.nille.http.route.exception.RouteExceptionHandler;
import be.nille.http.route2.Route;
import static com.google.common.base.Preconditions.checkArgument;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class HttpRouter {
    
   
    private int port;
    @Getter
    private final RouteRegistry registry;
    private final RouteExceptionHandler exceptionHandler;
    
    
    public HttpRouter(){
        this.exceptionHandler = new DefaultRouteExceptionHandler();
        this.registry = new RouteRegistry();
    }
       
    public HttpRouter listenTo(final int port){
        log.debug(String.format("Router will be listening to port %s", port));
        this.port = port;
        return this;
    }
    
    public HttpRouter addRoute(final Route route){
        this.registry.add(route);
        return this;
    }
    
   
    public void start() throws Exception{
        validate();
        HttpServer server = new ImmutableHttpServer(port, registry);
        server.run();
        
    }
    
 
    
    private void validate(){
        checkArgument(port >= 0, "the specified port %s should be larger than zero", port);
        checkArgument(registry.getRoutes().size() > 0, "there are no routes specified for this server");
    }
    
}
