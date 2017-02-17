/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.response;


import be.nille.http.router.media.TextMedia;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nholvoet
 */
public class ResponseBuilder {
    
    private final Map<String,String> headers;
    private Body body;
    private int statusCode;
   
    
    public ResponseBuilder(){
        headers = new HashMap<>();
        body = new Body(new TextMedia(""));
        statusCode = StatusCode.OK;
    }

    public ResponseBuilder withHeader(final String key, final String value){
        headers.put(key, value);
        return this;
    }

    public ResponseBuilder withBody(final Body body){
        this.body = body;
        return this;
    }

    public ResponseBuilder withStatusCode(final int statusCode){
        this.statusCode = statusCode;
        return this;
    }
    
    public Response build(){
        return new DefaultResponse(
               body, new StatusCode(statusCode), headers);
    }

   

}
