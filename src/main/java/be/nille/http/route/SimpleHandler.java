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
public class SimpleHandler implements RouteHandler {

    @Override
    public String handle(Request request) {
         return request.getURI();
    }
    
}
