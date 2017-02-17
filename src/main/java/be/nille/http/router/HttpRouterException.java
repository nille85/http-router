/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router;

import be.nille.http.router.request.Request;
import be.nille.http.router.response.StatusCode;
import lombok.Getter;

/**
 *
 * @author nholvoet
 */

public class HttpRouterException extends RuntimeException{
    
    @Getter
    private final Context context;
    @Getter
    private final Throwable cause;
    
    public HttpRouterException(final Context context, final Throwable throwable){
        super(String.format(
                "Router exception occurred, status code %s", context.getStatusCode().getValue())
        );
        this.context = context;
        this.cause = throwable;
    }
    
    @Getter
    public static class Context{
        private final StatusCode statusCode;
        private final Request request;
    
        public Context(final StatusCode statusCode, final Request request){
            this.statusCode = statusCode;
            this.request = request;
        }
    
    }
    
    
   
    
}
