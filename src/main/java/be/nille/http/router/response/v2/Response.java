/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.response.v2;



import be.nille.http.router.media.Media;
import be.nille.http.router.response.StatusCode;
import java.util.Map;

/**
 *
 * @author nholvoet
 */
public interface Response {

    Media getBody();

    StatusCode getStatusCode();

    Map<String, String> getHeaders();

}
