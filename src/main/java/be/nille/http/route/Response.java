/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author nholvoet
 */
@ToString
@Getter
public class Response {
    
    private final String content;
    private final String contentType;
    
    public Response(final String content, final String contentType){
        this.content = content;
        this.contentType = contentType;
    }
    
    
    
}
