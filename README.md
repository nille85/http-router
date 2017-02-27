# HTTP Router
The HTTP router is a non blocking object-oriented HTTP server based on Netty. An HTTP router consists of a socket port which is needed in order to start the server. 
The default port when no port is specified is `80`. An Http router also needs a route implementation.

Underneath you can find a very simple implementation. For every single HTTP request that is sent, the router will reply with HTTP status `200 OK` and the text `Hello World!` 
```
Route route = (request) -> new RouteResponse(new StatusCode(200), new TextBody("Hello World!"));
new HttpRouter(route).start();
```

##Route
Everything you pass to the HTTP router is a route. A route is a functional interface that can have an unlimited range of implementations.

```
@FunctionalInterface
public interface Route {
  
    Response response(Request request);

}
```


###MethodRoute
The framework uses the decorator pattern intensively. Every Route can be decorated with another route which makes the framework very flexible.
When the route underneath is passed to the router, every GET request will result in a status code 200 with the text Hello World. 
When another request method is used, the route will result in an EmptyResponse object for which the HTTP router will return the status code `404 Not Found`

```
Route origin = (request) -> new RouteResponse(new StatusCode(200), new TextBody("Hello World!"));
Route route = new MethodRoute(new Method(Method.GET), origin);
new HttpRouter(route).start();
```



###ListRoute

With the ListRoute implementation you can supply a list of routes. The first route that is matched will be used for creating the response.

```
List<Route> routes = new ArrayList<>();
Route getRoute = new MethodRoute(
        new Method(Method.GET),
        (request) -> new RouteResponse(new StatusCode(StatusCode.OK), new TextBody("Hello GET route"))
);
routes.add(getRoute);

Route postRoute = new MethodRoute(
        new Method(Method.POST),
        (request) -> new RouteResponse(new StatusCode(StatusCode.OK), new TextBody("Hello POST route"))
);
routes.add(postRoute);

Route deleteRoute = new MethodRoute(
        new Method(Method.DELETE),
        (request) -> new RouteResponse(new StatusCode(StatusCode.OK), new TextBody("Hello DELETE route"))
);
routes.add(deleteRoute);

Route listRoute = new ListRoute(routes);

new HttpRouter(listRoute).start();
```


###PathRoute
The PathRoute implementation can be parameterized. It can also be combined with other route implementations. The order of the route decoration does not make any difference.
Some examples can be found underneath.

```
List<Route> routes = new ArrayList<>();

Route methodPathRoute = new MethodRoute(
        new Method(Method.GET), 
        new PathRoute(
            "/subscriptions/:subscriptionId",
            (request) -> {
                return new RouteResponse(
                        new StatusCode(StatusCode.OK), 
                        new TextBody("subscription with id : " + request.variables().get("subscriptionId"))
                );
        }
));
routes.add(methodPathRoute);


Route pathMethodRoute =  new PathRoute(
        "/persons/:personId",
        new MethodRoute(
            new Method(Method.POST),
            (request) -> {
                return new RouteResponse(
                        new StatusCode(StatusCode.OK), 
                        new TextBody("person with id : " + request.variables().get("personId"))
                );
            }
));
routes.add(pathMethodRoute);

Route listRoute = new ListRoute(routes);

new HttpRouter(listRoute).start();
```




