/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route.response;

import java.util.Map;
import lombok.Getter;

/**
 *
 * @author nholvoet
 */
public interface Response {

    Map<String, String> getHeaders();

    Body getBody();

    StatusCode getStatusCode();

    ContentType getContentType();

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
