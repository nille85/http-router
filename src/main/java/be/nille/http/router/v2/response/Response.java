/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.response;



import be.nille.http.router.media.Media;
import java.util.Map;

/**
 *
 * @author nholvoet
 */
public interface Response {

    Media getBody();

    StatusCode getStatusCode();

    Map<String, String> getHeaders();
   
    Response response(int statusCode);
       
    Response response( Media body);
    
    Response response(final String key, final String value);

}
