/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.request;

import be.nille.http.router.route.Method;
import be.nille.http.router.route.Path;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 *
 * @author nholvoet
 */
public interface Request {

    Method getMethod();

    Path getPath();

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
    
    @Getter
    public static class MetaData {
        
        private final boolean keepAlive;
        private final Charset charset;
        
        public MetaData(final boolean keepAlive, final Charset charset){
            this.keepAlive = keepAlive;
            this.charset = charset;
        }
    }

    
}
