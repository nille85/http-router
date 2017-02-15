/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route.response;

import be.nille.http.route.response.Response.Body;
import be.nille.http.route.response.Response.ContentType;
import be.nille.http.route.response.Response.StatusCode;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

/**
 *
 * @author nholvoet
 */
public class ResponseBuilder {
    
    private final Map<String,String> headers;
    private String body;
    private int statusCode;
    private String contentType;
    
    public ResponseBuilder(){
        headers = new HashMap<>();
        body = "";
        statusCode = StatusCode.OK;
        contentType = "text/plain; charset=UTF-8";      
    }

    public ResponseBuilder withHeader(final String key, final String value){
        headers.put(key, value);
        return this;
    }

    public ResponseBuilder withBody(final String body){
        this.body = body;
        return this;
    }

    public ResponseBuilder withStatusCode(final int statusCode){
        this.statusCode = statusCode;
        return this;
    }

    public ResponseBuilder withContentType(final String contentType){
        this.contentType = contentType;
        return this;
    }
    
    public Response build(){
        return new Response(
                new Body(body), new StatusCode(statusCode), new ContentType(contentType), headers);
    }

   

}
