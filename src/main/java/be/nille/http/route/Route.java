/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author nholvoet
 */
@Getter
@ToString
public class Route {
    
    
    
    private final Path path;
    private final RequestMethod method;
    private final RouteHandler handler;
    
    
    public Route(final String path, final String requestMethod, final RouteHandler handler){
        this(new Path(path), new RequestMethod(requestMethod), handler);
    }
    
    public Route(final Path path, final RequestMethod method, final RouteHandler handler){
        this.path = path;
        this.method = method;
        this.handler = handler;
    }
    
    
    
    
    
    
    
}
