/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.request;

import be.nille.http.router.body.Body;
import be.nille.http.router.headers.Headers;
import java.net.URI;

/**
 *
 * @author Niels Holvoet
 */
public interface Request {
    
    Headers getHeaders();
    
    Body getBody();
    
    URI getURI();
    
    Method getMethod();
    
    QueryParameters queryParameters();
    
    PathVariables variables();

    public String getPath();
    
}
