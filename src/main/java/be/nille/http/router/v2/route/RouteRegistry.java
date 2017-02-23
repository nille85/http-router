/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;

import be.nille.http.router.v2.response.StatusCodeException;
import be.nille.http.router.request.Request;
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
public final class RouteRegistry {

    @Getter
    private final List<Route> routes;

    public RouteRegistry() {
        routes = new ArrayList<>();
    }

    private RouteRegistry(List<Route> routes) {
        this.routes = routes;
    }

    public RouteRegistry add(final Route route) {
        List<Route> copiedRoutes = this.routes;
        copiedRoutes.add(route);
        return new RouteRegistry(copiedRoutes);
    }

    public Route find(final Request request) throws StatusCodeException {
        for (Route route : routes) {
            if (route.matches(request)) {
                return route;
            }
        }
        throw new StatusCodeException(new StatusCode(StatusCode.NOT_FOUND));
    }

}
