/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route.request;

import be.nille.http.route2.Method;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 *
 * @author nholvoet
 */
public interface Request {

    Method getMethod();

    URI getUri();

    Map<String, List<String>> getQueryParameters();
    
    Map<String, String> getPathParameters();

    Map<String, String> getHeaders();

    Body getBody();
    
    
    public static RequestBuilder builder(){
        return new RequestBuilder();
    }

    @Getter
    public static class Body {

        private final String value;

        public Body(final String value) {
            this.value = value;
        }

    }

    
}
