/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router;

import be.nille.http.router.request.Request;
import lombok.Getter;

/**
 *
 * @author nholvoet
 */
@Getter
public class HttpClientException extends RuntimeException {
    
    private final Request request;
    private final Throwable cause;
    
    public HttpClientException(final Request request, final Throwable cause){
        this.request = request;
        this.cause = cause;
    }
    
    @Override
    public String getMessage(){
        return String.format("Error executing request %s", request);
    }
    
}
