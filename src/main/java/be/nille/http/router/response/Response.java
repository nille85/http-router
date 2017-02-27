/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.response;



import be.nille.http.router.body.Body;
import be.nille.http.router.headers.Headers;

/**
 *
 * @author nholvoet
 */
public interface Response {

    Body getBody();

    StatusCode getStatusCode();

    Headers getHeaders();
   
    boolean isEmpty();

}
