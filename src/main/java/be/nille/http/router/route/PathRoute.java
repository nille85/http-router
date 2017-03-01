/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.request.PathVariables;
import be.nille.http.router.request.Request;
import be.nille.http.router.request.RouteRequest;
import be.nille.http.router.response.EmptyResponse;
import be.nille.http.router.response.Response;
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
            return origin.response(
                    new RouteRequest(
                            request,
                            new PathVariables(path.parameterMap(request))
                    )
            );
        }
        return new EmptyResponse();
    }

 
}
