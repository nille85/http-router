/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.request.Request;
import be.nille.http.router.request.RouteRequest;
import be.nille.http.router.response.EmptyResponse;
import be.nille.http.router.response.StatusCodeException;
import be.nille.http.router.response.Response;
import be.nille.http.router.response.StatusCode;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public final class ListRoute implements Route {


    private final List<Route> routes;

    public ListRoute(final List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public Response response(final Request request){
        Response response = new EmptyResponse();
        for (Route route : routes) {
            response = route.response(request);
            if(!response.isEmpty()){
                return response;
            }
        }
       return response;
    }

}
