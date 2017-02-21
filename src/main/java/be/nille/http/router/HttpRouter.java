/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router;

import be.nille.http.router.exception.DefaultExceptionHandler;
import be.nille.http.router.exception.ExceptionHandler;
import be.nille.http.router.netty.NettyHttpServer;
import be.nille.http.router.route.DefaultRoute;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class HttpRouter implements Router {

    private final RouteRegistry registry;

    private final HttpServer httpServer;

    public HttpRouter(final int port) {
        this(new NettyHttpServer(new Port(port).getValue(), new DefaultExceptionHandler()));
    }

    public HttpRouter(final int port, final ExceptionHandler exceptionHandler) {
        this(new NettyHttpServer(new Port(port).getValue(), exceptionHandler));
    }

    public HttpRouter(final Port port) {
        this(new NettyHttpServer(port.getValue(), new DefaultExceptionHandler()));
    }

    public HttpRouter(final Port port, final ExceptionHandler exceptionHandler) {
        this(new NettyHttpServer(port.getValue(), exceptionHandler));
    }

    public HttpRouter(final HttpServer httpServer) {
        this.registry = new RouteRegistry();
        this.httpServer = httpServer;

    }

    @Override
    public void addRoute(final DefaultRoute route) {
        this.registry.add(route);
        log.info(String.format("Route added with path %s and method %s", route.getPath(), route.getMethod()));
       
    }

    @Override
    public void start() {
        validate();
        try {
            httpServer.run(registry);
        } catch (Exception ex) {
            throw new RuntimeException("The Http router could not be started", ex);
        }

    }

    @Override
    public List<DefaultRoute> getRoutes() {
        return registry.getRoutes();
    }

    private void validate() {
        if (registry.getRoutes().isEmpty()) {
            throw new RuntimeException("There are no routes specified for this server");
        }
    }

}
