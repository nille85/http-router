/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;

import be.nille.http.router.request.Request;
import be.nille.http.router.v2.response.Response;


/**
 *
 * @author nholvoet
 */
@FunctionalInterface
public interface RouteCallback {
    
    
    Response handle(Request request);
    
}
