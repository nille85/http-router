/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.request.Method;
import be.nille.http.router.request.Request;
import be.nille.http.router.response.EmptyResponse;
import be.nille.http.router.response.Response;

/**
 *
 * @author Niels Holvoet
 */
public final class MethodRoute implements Route{
    
    private final Method method;
    private final Route origin;
    
    public MethodRoute(final Method method, final Route route){
        this.method = method;
        this.origin = route;
    }

    @Override
    public Response response(Request request) {
        if(method.equals(request.getMethod())){
            return origin.response(request);
        }
        return new EmptyResponse();
        
    }
    
}
