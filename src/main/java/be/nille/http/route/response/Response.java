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
@Getter
public class Response {
    
    private final Body body;
    private final StatusCode statusCode;
    private final ContentType contentType;
    private final Map<String,String> headers;
    
    
    public Response(Body body){
        this(body, new StatusCode(StatusCode.OK), new ContentType("text/html; charset=text/plain"), new HashMap<>());
    }
    
    public static ResponseBuilder builder(){
        return new ResponseBuilder();
    }
    
    
    public Response(Body body, StatusCode statusCode, ContentType contentType, Map<String, String> headers){
        this.body = body;
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.headers = headers;
    }
    
     @Getter
    public static class Body {

        private final String value;

        public Body(final String value) {
            this.value = value;
        }

    }

    @Getter
    public static class StatusCode {

        public static final int OK = 200;
        public static final int NOT_FOUND = 404;
        public static final int METHOD_NOT_ALLOWED = 405;
        public static final int INTERNAL_SERVER_ERROR = 500;

        private final int value;

        public StatusCode(final int value) {
            this.value = value;
        }

    }

    @Getter
    public static class ContentType {

        private final String value;

        public ContentType(final String value) {
            this.value = value;
        }

    }

    
}
