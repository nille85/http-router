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
    
    public static enum Method{
        GET,POST
    }
    
    private final Path path;
    private final Method method;
    
    public Route(final Path path, final Method method){
        this.path = path;
        this.method = method;
    }
    
    
    
    
    
    
    
}
