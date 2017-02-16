/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.response;

import be.nille.http.router.response.Response.Body;
import be.nille.http.router.response.Response.StatusCode;
import java.util.Map;
import lombok.Getter;

/**
 *
 * @author nholvoet
 */
public interface Response {

    Body getBody();

    StatusCode getStatusCode();

    Map<String, String> getHeaders();

    static ResponseBuilder builder() {
        return new ResponseBuilder();
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
        public static final int BAD_REQUEST = 400;
        public static final int NOT_FOUND = 404;
        public static final int METHOD_NOT_ALLOWED = 405;
        public static final int INTERNAL_SERVER_ERROR = 500;

        private final int value;

        public StatusCode(final int value) {
            this.value = value;
        }

    }


}
