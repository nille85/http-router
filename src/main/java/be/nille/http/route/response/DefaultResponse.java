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
    
    private final Body body;
    private final StatusCode statusCode;
    private final ContentType contentType;
    private final Map<String,String> headers;
    
    
    public DefaultResponse(Body body){
        this(body, new StatusCode(StatusCode.OK), new ContentType("text/html; charset=text/plain"), new HashMap<>());
    }
    
    
    public DefaultResponse(Body body, StatusCode statusCode, ContentType contentType, Map<String, String> headers){
        this.body = body;
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.headers = headers;
    }

    
}
