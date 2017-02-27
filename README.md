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
The PathRoute implementation can be parameterized. It can also be combined with other route implementations. The outer routes are always first evaluated. 

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

routes.add(otherRoute);
...

Route listRoute = new ListRoute(routes);

new HttpRouter(listRoute).start();
```

###RegexRoute
A route can also be added using a regex. The groups can be captured from the request using the index of the regex group. 
```
RegexRoute regexRoute = new RegexRoute(
        "^/subscriptions/(.*)$",
        (request) -> new RouteResponse(
                new StatusCode(StatusCode.OK),
                new TextBody(request.variables().get("1"))
        )
);

```

##Building Custom Routes

Custom routes can easily be created by implementing the `Route` interface.
 
###LoggingRoute

```
@Slf4j
public final class LoggingRoute implements Route {
    
    private final Route origin;
    
    public LoggingRoute(final Route route){
        this.origin = route;
    }

    @Override
    public Response response(Request request) {   
        log.debug(String.format(
                        "evaluating request with method %s and path %s",
                        request.getMethod(),
                        request.getPath()
                )
        );
        return origin.response(request);
    }
    
}
```


Logging can then be added to all routes using the decoration underneath.
```
Route listRoute = new LoggingRoute(new ListRoute(routes));
new HttpRouter(listRoute).start();
```


###AuthenticatedRoute
The example underneath checks if a request header `X-AUTH` with the value `secret` is present for the matched route.
If the request does not contain this header, a status code `401` is returned.
```
public final class AuthenticatedRoute implements Route {
    
    private final Route origin;
    
    public AuthenticatedRoute(final Route route){
        this.origin = route;
    }

    @Override
    public Response response(Request request) {   
        String authorizationValue = request.getHeaders().getValue("X-AUTH");
        if("secret".equals(authorizationValue)){
            return origin.response(request);
        }
        return new RouteResponse(new StatusCode(401),new TextBody("Unauthorized"),new Headers().add("content-type", "text/html"));
        
    }
    
}
```
It can then be used to protect certain routes. In the example below all routes are matched that start with `/protected/`.
```
 Route protectedRoute = new RegexRoute(
        "^/protected/.*$",
        new AuthenticatedRoute((request) -> {
                    return new RouteResponse(
                            new StatusCode(StatusCode.OK),
                            new TextBody("This is a protected route")
                    );
                    }
        )
);

```

###InterceptRequestRoute
You can also easily intercept requests and pass it on to the next route.
```
public class InterceptRequestRoute implements Route{
    
    private final Route origin;
    
    public InterceptRequestRoute(final Route route){
        this.origin = route;
    }

    @Override
    public Response response(Request request) {
        Request interceptedRequest = new RouteRequest(
                request.getMethod(),
                request.getURI(),
                request.getBody(),
                request.getHeaders().add("X-CUSTOM", "value")
        );
        return origin.response(interceptedRequest);
    }
    
}

##Other Media Types
You can return JSON by using JsonBody instead of TextBody. The content-type header also needs to be added. 

```
Route jsonRoute = new PathRoute("/json",
                            (request) -> {
                            return new RouteResponse(
                                    new StatusCode(StatusCode.OK),
                                    new JsonBody(new Person("John","Doe")),
                                    new Headers().add("content-type", "application/json")
                            );
                        }
                  );

```
Other Media Types can be added by implementing the Body Interface.
```
public interface Body {
      
    String print();
      
}
```

##Content Negotiation
Below you can find a simple JSON content-negotiation implementation .

```
public final class JSONRoute implements Route{

    private final Route origin;
    
    public JSONRoute(final Route route){
        this.origin = route;
    }
    
    
    @Override
    public Response response(Request request) {
        final String acceptValue = request.getHeaders().getValue("Accept");
        if(acceptValue != null && acceptValue.contains("application/json")){
            Response response = origin.response(request);
            return new RouteResponse(response.getStatusCode(), response.getBody(), response.getHeaders().add("Content-Type", "application/json"));
        }
        return new EmptyResponse();
    }
    
}

```
When used, you don't need to add the content-type header for every response.
```
Route jsonRoutes = new JSONRoute(
                new RegexRoute("/json",
                        (request) -> {
                            return new RouteResponse(
                                    new StatusCode(StatusCode.OK),
                                    new JsonBody(new Person("John", "Doe"))
                            );

                        })
        );

```


##Unit Testing
Unit testing new routes can be done in the same way like the unit tests that are present in this library.
```
public class MethodRouteTest {
    
    private Route origin;
    
    @Before
    public void setup(){
        origin = (request) -> new RouteResponse(new StatusCode(StatusCode.OK), new TextBody("Hello World"));
    }
    
    
    @Test
    public void responseShouldBeNotEmptyWhenMethodsAreEqual() throws URISyntaxException{
        Route route = new MethodRoute(new Method(Method.GET), origin);
        Request request = new RouteRequest(new Method(Method.GET), new URI("http://localhost/test"));
        Response response = route.response(request);
        assertFalse(response.isEmpty());
        //test other attributes of response
    }
}
```




