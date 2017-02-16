/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route.exception;

/**
 *
 * @author nholvoet
 */
public class HttpRouterException extends RuntimeException {
    
    
    public HttpRouterException(final String message, final Throwable throwable){
        super(message, throwable);
    }
    
    public HttpRouterException(final String message){
        super(message);
    }
}
