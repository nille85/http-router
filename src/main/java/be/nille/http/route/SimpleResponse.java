/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author nholvoet
 */
@ToString
@Getter
public class SimpleResponse implements Response {
    
    
    private final String content;
    private final String contentType;
    private final int statusCode;
    
    
    public SimpleResponse(final String content, final String contentType){
       this(content,contentType,200);
    }
    
    public SimpleResponse(final String content, final String contentType, final int statusCode){
        this.content = content;
        this.contentType = contentType;
        this.statusCode = statusCode;
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", contentType);
        return headers;
    }
    
    
    
}
