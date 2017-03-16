/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.response;

import be.nille.http.router.body.Body;
import be.nille.http.router.body.TextBody;
import be.nille.http.router.headers.Headers;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import lombok.ToString;

/**
 *
 * @author nholvoet
 */
@ToString
public final class RouteResponse implements Response {

    private final StatusCode statusCode;
    private final Body body;
    private final Headers headers;

    public RouteResponse() {
        this(new StatusCode(200), new TextBody(""), new Headers());
    }

    public RouteResponse(final int statusCode, final Body body) {
        this(new StatusCode(statusCode), body, new Headers());
    }

    public RouteResponse(final StatusCode statusCode, final Body body) {
        this(statusCode, body, new Headers());
    }

    public RouteResponse(final StatusCode statusCode, final Body body, final Headers headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public RouteResponse withHeader(final String key, final String value) {
        return new RouteResponse(this.statusCode, body, headers.add(key, value));
    }

    @Override
    public Body getBody() {
        return this.body;
    }

    @Override
    public StatusCode getStatusCode() {
        return this.statusCode;
    }

    @Override
    public Headers getHeaders() {
        return headers;
    }
     

    @Override
    public boolean isEmpty() {
        return false;
    }

}
