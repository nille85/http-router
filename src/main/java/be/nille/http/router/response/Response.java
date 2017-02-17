/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.response;


import be.nille.http.router.media.Body;
import java.util.Map;

/**
 *
 * @author nholvoet
 */
public interface Response {

    Body getBody();

    StatusCode getStatusCode();

    Map<String, String> getHeaders();

    
    static ResponseBuilder builder() {
        return new ResponseBuilder();
    }
    
    

   

    


}
