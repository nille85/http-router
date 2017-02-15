/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route2;

import be.nille.http.route2.Method;
import be.nille.http.route2.Path;
import be.nille.http.route2.RequestHandler;
import be.nille.http.route2.Route;
import com.google.common.base.Preconditions;

/**
 *
 * @author nholvoet
 */
public class RouteBuilder {

    private Method method;
    private RequestHandler handler;
    private Path path;
   

    public RouteBuilder() { 
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

    public Route build() {
        validate();
        return new Route(method, path, handler);
        
    }

    private void validate() {
        Preconditions.checkNotNull(method, "Request method cannot be null");
        Preconditions.checkNotNull(path, "Path cannot be null");
        Preconditions.checkNotNull(handler, "Handler method cannot be null");
    }

}
