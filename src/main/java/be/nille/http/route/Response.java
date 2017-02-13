/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

import java.util.Map;

/**
 *
 * @author Niels Holvoet
 */
public interface Response {
    
    String getContent();
    
    Map<String,String> getHeaders();
    
    int getStatusCode();
    
}