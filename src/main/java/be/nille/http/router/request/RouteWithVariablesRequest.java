/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.request;

import be.nille.http.router.body.Body;
import be.nille.http.router.headers.Headers;
import java.net.URI;
import java.util.Map;

/**
 *
 * @author Niels Holvoet
 */
public class RouteWithVariablesRequest implements Request {

    private final Request origin;
    private final Map<String, String> variablesMap;

    public RouteWithVariablesRequest(final Request request, final Map<String, String> variablesMap) {
        this.origin = request;
        this.variablesMap = variablesMap;
    }

    @Override
    public Headers getHeaders() {
        return origin.getHeaders();
    }

    @Override
    public Body getBody() {
        return origin.getBody();
    }

    @Override
    public Method getMethod() {
        return origin.getMethod();
    }

    @Override
    public QueryParameters queryParameters() {
        return origin.queryParameters();
    }

    @Override
    public PathVariables variables() {
        return new PathVariables(variablesMap);
    }

    @Override
    public String getPath() {
        return origin.getPath();
    }

    @Override
    public URI getURI() {
        return origin.getURI();
    }

}
