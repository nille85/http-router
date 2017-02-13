/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

/**
 *
 * @author nholvoet
 */
@FunctionalInterface
public interface RouteHandler {
    
    
    Response handle(Request request);
    
}
