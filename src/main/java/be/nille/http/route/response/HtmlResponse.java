/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route.response;

import java.util.Map;

/**
 *
 * @author nholvoet
 */
public class HtmlResponse implements Response {
    
    private final Response origin;
    
    public HtmlResponse(final Response response){
        this.origin = response;
    }

    @Override
    public Body getBody() {
        return origin.getBody();
    }

    @Override
    public StatusCode getStatusCode() {
        return origin.getStatusCode();
    }

    @Override
    public Map<String, String> getHeaders() {
        Map headers = origin.getHeaders();
        headers.put("Content-Type", "text/html;charset=utf-8");
        return headers;
    }
    
}
