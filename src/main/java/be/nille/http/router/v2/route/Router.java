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
import com.google.common.collect.ImmutableList;
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


    private final List<RouteCallback> callbacks;

    public Router() {
        callbacks = ImmutableList.of();
    }

    private Router(final List<RouteCallback> callbacks) {
        this.callbacks = callbacks;
    }

    public Router addCallback(final RouteCallback callback) {
        List<RouteCallback> copy = new ArrayList<>(callbacks);
        copy.add(callback);
        return new Router(ImmutableList.copyOf(copy));
    }
    
    List<RouteCallback> getCallbacks(){
        return ImmutableList.copyOf(callbacks);
    }

    public Response evaluate(final RouterRequest request) throws StatusCodeException {
        for (RouteCallback callback : callbacks) {
            if (callback.matches(request)) {
                return callback.execute(request);              
            }
        }
        throw new StatusCodeException(new StatusCode(StatusCode.NOT_FOUND));
    }

}
