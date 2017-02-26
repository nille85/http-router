/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;

import be.nille.http.router.v2.request.Method;
import be.nille.http.router.v2.request.Request;

/**
 *
 * @author Niels Holvoet
 */
public class MethodRoute implements Route{
    
    private final Method method;
    
    public MethodRoute(final Method method){
        this.method = method;
    }

    @Override
    public boolean matches(Request request) {
       return method.equals(request.getMethod());
    }
    
}
