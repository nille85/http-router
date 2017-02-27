/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.response;

import lombok.Getter;

/**
 *
 * @author nholvoet
 */
@Getter
public class StatusCodeException extends Exception {
    
    private final StatusCode statusCode;
    
    public StatusCodeException(final StatusCode statusCode){
        super(String.format(
                "Status code exception occurred, status code %s", statusCode.getValue())
        );
        this.statusCode = statusCode;
    }
    
    
    
    
}
