/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.request;

import be.nille.http.router.media.Media;
import java.util.Map;

/**
 *
 * @author Niels Holvoet
 */
public interface Request {
    
    Map<String,String> getHeaders();
    
    Media getBody();
    
    Method getMethod();
    
    QueryParameters queryParameters();

    public String getPath();
    
}
