/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router;

/**
 *
 * @author nholvoet
 */
public class MethodNotFoundException extends RuntimeException {
    
    
    public MethodNotFoundException(final String message){
        super(message);
    }
}
