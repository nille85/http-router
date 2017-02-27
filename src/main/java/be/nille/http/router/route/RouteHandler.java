/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;


import be.nille.http.router.request.Request;
import be.nille.http.router.response.Response;


/**
 *
 * @author nholvoet
 */

public interface RouteHandler extends Route {
        
    Response execute(Request request);
    
}
