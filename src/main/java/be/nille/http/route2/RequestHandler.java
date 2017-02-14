/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route2;

import be.nille.http.route.request.Request;
import be.nille.http.route.response.Response;

/**
 *
 * @author nholvoet
 */
@FunctionalInterface
public interface RequestHandler {
    
    
    Response handle(Request request);
    
}
