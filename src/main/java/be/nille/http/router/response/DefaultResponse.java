/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.response;

import be.nille.http.router.media.Body;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author nholvoet
 */
@Getter
@ToString
public class DefaultResponse implements Response {
    
    private final Body body;
    private final StatusCode statusCode;
    private final Map<String,String> headers;
    
    
    public DefaultResponse(Body body){
        this(body, new StatusCode(StatusCode.OK), new HashMap<>());
    }
    
    
    public DefaultResponse(Body body, StatusCode statusCode, Map<String, String> headers){
        this.body = body;
        this.statusCode = statusCode;
        this.headers = headers;
    }
    
    public static ResponseBuilder builder(){
        return new ResponseBuilder();
    }
    
}
