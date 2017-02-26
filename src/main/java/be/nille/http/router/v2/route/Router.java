/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;

import be.nille.http.router.v2.request.Request;
import be.nille.http.router.v2.request.RouterRequest;
import be.nille.http.router.v2.response.StatusCodeException;

import be.nille.http.router.v2.response.Response;

import be.nille.http.router.v2.response.StatusCode;
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
public final class Router {


    private final List<RouteHandler> handlers;

    public Router() {
        handlers = ImmutableList.of();
    }

    private Router(final List<RouteHandler> handlers) {
        this.handlers = handlers;
    }

    public Router addHandler(final RouteHandler handler) {
        List<RouteHandler> copy = new ArrayList<>(handlers);
        copy.add(handler);
        return new Router(ImmutableList.copyOf(copy));
    }
    
    List<RouteHandler> getHandlers(){
        return ImmutableList.copyOf(handlers);
    }

    public Response response(final Request request) throws StatusCodeException {
        for (RouteHandler handler : handlers) {
            if(handler.matches(request)){
                return handler.execute(request);  
            }
           
        }
        throw new StatusCodeException(new StatusCode(StatusCode.NOT_FOUND));
    }

}
