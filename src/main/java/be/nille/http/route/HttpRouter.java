/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

import be.nille.http.ImmutableHttpServer;
import be.nille.http.HttpServer;
import static com.google.common.base.Preconditions.checkArgument;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class HttpRouter {
    
    private boolean started;
    private int port;
    @Getter
    private final RouteRegistry registry;
    
    public HttpRouter(){
        this.started = false;
        this.registry = new RouteRegistry(this);
    }
       
    public HttpRouter listenTo(final int port){
        log.debug(String.format("Router will be listening to port %s", port));
        this.port = port;
        return this;
    }
    
    
    public RouteBuilder addRoute(){
        log.debug("adding route ... ");
        return new RouteBuilder(this);
    }
    
    
    public void start() throws Exception{
        validate();
        HttpServer server = new ImmutableHttpServer(port, registry);
        server.run();
        started = true;
    }
    
    public boolean isStarted(){
        return started;
    }
    
    private void validate(){
        checkArgument(port >= 0, "the specified port %s should be larger than zero", port);
        checkArgument(registry.getRoutes().size() > 0, "there are no routes specified for this server");
    }
    
}
