/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;


import be.nille.http.router.v2.request.Request;

/**
 *
 * @author nholvoet
 */
public interface Route {
  
    boolean matches(Request request);

}
