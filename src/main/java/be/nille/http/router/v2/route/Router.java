/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;

import be.nille.http.router.v2.request.RouterRequest;
import be.nille.http.router.v2.response.StatusCodeException;

import be.nille.http.router.v2.response.Response;

import be.nille.http.router.v2.response.StatusCode;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public final class Router {

    @Getter
    private final List<Route> routes;

    public Router() {
        routes = new ArrayList<>();
    }

    private Router(List<Route> routes) {
        this.routes = routes;
    }

    public Router add(final Route route) {
        List<Route> copiedRoutes = this.routes;
        copiedRoutes.add(route);
        return new Router(copiedRoutes);
    }

    public Response evaluate(final RouterRequest request) throws StatusCodeException {
        for (Route route : routes) {
            if (route.matches(request)) {
                return route.execute(request);              
            }
        }
        throw new StatusCodeException(new StatusCode(StatusCode.NOT_FOUND));
    }

}
