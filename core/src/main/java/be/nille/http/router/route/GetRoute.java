package be.nille.http.router.route;

import be.nille.http.router.request.Method;
import be.nille.http.router.request.Request;
import be.nille.http.router.response.Response;

/**
 * Created by Niels on 19/03/2017.
 */
public final class GetRoute implements Route {

    private Route origin;

    public GetRoute(final PathRoute route){
        origin = new MethodRoute(new Method(Method.GET), route);
    }

    public GetRoute(final RegexRoute route){
        origin = new MethodRoute(new Method(Method.GET), route);
    }

    @Override
    public Response response(Request request) {
        return origin.response(request);
    }
}
