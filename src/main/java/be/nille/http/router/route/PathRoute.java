/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.request.Request;
import be.nille.http.router.request.RequestMatcher;
import be.nille.http.router.request.RouteWithVariablesRequest;
import be.nille.http.router.response.EmptyResponse;
import be.nille.http.router.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;

/**
 *
 * @author Niels Holvoet
 */
public class PathRoute implements Route {

    @Getter
    private final Path path;
    private final Route origin;


    public PathRoute(final String path, final Route route) {
        this.path = new Path(path);
        this.origin = route;
    }

    @Override
    public Response response(Request request) {
        if (path.matches(request)) {
            RouteWithVariablesRequest dRequest = new RouteWithVariablesRequest(request, path.parameterMap(request));
            return origin.response(dRequest);
        }
        return new EmptyResponse();
    }

 
}
