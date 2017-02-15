/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

import be.nille.http.route.HttpRouter;
import be.nille.http.route.RouteRegistry;
import be.nille.http.route2.Method;
import be.nille.http.route2.Path;
import be.nille.http.route2.RequestHandler;
import be.nille.http.route2.Route;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class RouteBuilder {

    private Method method;
    private RequestHandler handler;
    private Path path;
    private final HttpRouter router;
    
   

    public RouteBuilder(final HttpRouter router) {
        this.router = router;
       
    }

    public RouteBuilder withMethod(final Method method) {
        this.method = method;
        return this;
    }
    
    public RouteBuilder withMethod(final String method) {
        this.method = new Method(method);
        return this;
    }

    public RouteBuilder withHandler(final RequestHandler handler) {
        this.handler = handler;
        return this;
    }

    public RouteBuilder withPath(final Path path) {
        this.path = path;
        return this;
    }
    
    public RouteBuilder withPath(final String path) {
        this.path = new Path(path);
        return this;
    }

    public HttpRouter save() {
        validate();
        Route route = new Route(method, path, handler);
        log.debug(String.format("adding route to router: %s", route.toString()));
        router.getRegistry().add(route);
        return router;
        
    }

    private void validate() {
        Preconditions.checkNotNull(method, "Request method cannot be null");
        Preconditions.checkNotNull(path, "Path cannot be null");
        Preconditions.checkNotNull(handler, "Handler method cannot be null");
    }

}
