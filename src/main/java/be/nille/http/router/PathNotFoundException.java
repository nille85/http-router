/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router;

import be.nille.http.route.exception.HttpRouterException;

/**
 *
 * @author nholvoet
 */
public class PathNotFoundException extends HttpRouterException {
    
    
    public PathNotFoundException(final String message){
        super(message);
    }
}
