/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route.response;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

/**
 *
 * @author nholvoet
 */
@Getter
public class DefaultResponse implements Response {
    
    private final Response.Body body;
    private final Response.StatusCode statusCode;
    private final Map<String,String> headers;
    
    
    public DefaultResponse(Response.Body body){
        this(body, new Response.StatusCode(Response.StatusCode.OK), new HashMap<>());
    }
    
    
    public DefaultResponse(Response.Body body, Response.StatusCode statusCode, Map<String, String> headers){
        this.body = body;
        this.statusCode = statusCode;
        this.headers = headers;
    }
    
    public static ResponseBuilder builder(){
        return new ResponseBuilder();
    }
    
    
}
