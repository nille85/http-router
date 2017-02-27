/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import be.nille.http.router.body.Body;
import be.nille.http.router.body.TextBody;
import be.nille.http.router.headers.Headers;
import be.nille.http.router.request.QueryParameters;
import be.nille.http.router.request.Request;
import be.nille.http.router.request.Method;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nholvoet
 */
public class NettyRequest implements Request {

    private final HttpRequest httpRequest;
    private final String httpContent;

    public NettyRequest(final HttpRequest httpRequest, final String httpContent) {
        this.httpRequest = httpRequest;
        this.httpContent = httpContent;
    }

    @Override
    public Headers getHeaders() {
        HttpHeaders headers = httpRequest.headers();

        Headers copiedHeaders = new Headers();

        if (!headers.isEmpty()) {
            for (Map.Entry<String, String> h : headers) {
                copiedHeaders = copiedHeaders.add(h.getKey(), h.getValue());
            }
        }
        return copiedHeaders;
    }

    @Override
    public Body getBody() {
        return new TextBody(httpContent);
    }

    @Override
    public Method getMethod() {
        return new Method(httpRequest.method().name());
    }

    @Override
    public QueryParameters queryParameters() {
        try {
            return new QueryParameters(new URI(httpRequest.uri()));
        } catch (URISyntaxException ex) {
            throw new RuntimeException(
                    String.format("The value %s is not a valid URI ", httpRequest.uri()),
                    ex
            );
        }
    }

    @Override
    public String getPath() {
        try {
            return new URI(httpRequest.uri()).getPath();
        } catch (URISyntaxException ex) {
            throw new RuntimeException(
                    String.format("The value %s is not a valid URI ", httpRequest.uri()),
                    ex
            );
        }
    }

}
