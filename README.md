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



###Initializing a Router
```
HttpRouter router = new HttpRouter(80)

```
###Adding Routes
Before starting the router, routes need to be added. A route consists of a HTTP method, the path of the resource and a request handler. The request handler defines what needs to be done with the request when it arrives at the defined path. A request handler returns a response.
```        
router.addRoute(
              new Route(
                      new Method(Method.GET),
                      new Path("/subscriptions"),
                      new SubscriptionsRequestHandler()
              )
);

router.addRoute(
              new Route(
                      new Method(Method.POST),
                      new Path("/:personId/subscriptions"),
                      new AddSubscriptionForPersonHandler()

              )
);

```



###Starting a router

In order to start a router, only the start method needs to be called. Routes can also be added after the router was allready started.

```
router.start();
```

###Stopping a router

A router can also be programmatically stopped.

```
router.stop();
```


##Request Handler
Request handlers return a response based on an incoming request when a route was matched.

###A Simple Example
The request handler underneath returns a response with a plain text message containing the text `Hello world`. The HTTP status code that is returned is a `200 OK`
```
public class SimpleRequestHandler implements RequestHandler {

    @Override
    public Response handle(Request request) {
        return Response.builder()
                .withBody(new Body(new TextMedia("Hello world")))
                .withStatusCode(StatusCode.OK)
                .withHeader("Content-Type", "text/plain")
                .build();
    }

}

```

##Java 8

It is also possible to implement Request Handlers using lambda expressions

```    
    router.addRoute(new Route(
                Method.GET,
                "/subscriptions",
                (request) -> Response.builder().withBody("fetched subscriptions").build()
        ));
```

###Handling Query Parameters And Custom Media

The example underneath reads the query parameter `count` from the request. Based on that parameter it creates a number of articles. The articles are transformed into JSON and returned to the client.
```
public class GetArticlesRequestHandler implements RequestHandler {

    @Override
    public Response handle(Request request) {

        Count count = new Count(request);
        List<Article> articles = new ArrayList<>();
        for (int i = 1; i <= count.getValue(); i++) {
            articles.add(new Article(
                    "Title " + i,
                    "Text " + i)
            );
        }
        JsonMedia media = new JsonMedia(articles);

        return Response.builder()
                .withBody(new Body(media))
                .withStatusCode(200)
                .withHeader("Content-Type", "application/json")
                .build();

    }
    
    private static class Count{
        
        private final Request request;
        
        Count(final Request request){
            this.request = request;
        }
        
        int getValue(){
            List<String> values = request.getQueryParameters().get("count");
            if(!values.isEmpty()){
                int value = Integer.parseInt(values.get(0));
                return value;
            }
            throw new IllegalArgumentException("Invalid count parameter");
        }
    }

}


```


##Path Parameters

Path parameters can be specified using a `:` followed by the variable name

When the route underneath is added, you can get the value of the path parameter by calling `request.getPathParameters().get("personId")` in the corresponding handler.

```
router.addRoute(
              new Route(
                      new Method(Method.POST),
                      new Path("/:personId/subscriptions"),
                      new AddSubscriptionForPersonHandler()

              )
);
```

```
public class AddSubscriptionForPersonHandler implements RequestHandler {

    @Override
    public Response handle(Request request) {
         final String personId = request.getPathParameters().get("personId");
         
         System.out.println("received body content:" + request.getBody().getValue());
         
         return Response.builder()
                 .withBody("you asked about person with id " + personId)
                 .withStatusCode(201)
                 .build();
    }
    
}
```



##Exception Handling

A custom exception handler can be specified when initializing the Http Router

```
HttpRouter router = new HttpRouter(8080, new CustomExceptionHandler());

```

Only one method needs to implemented. The HttpRouterException is passed as a parameter to the function. This exception contains context information about the request and the status code. This allows you to tailor the error responses to your needs. 

```
public class CustomExceptionHandler implements ExceptionHandler {

    @Override
    public Response handleException(HttpRouterException hre) {
        HttpRouterException.Context context = hre.getContext();
        Request request = context.getRequest();
        
        Media media = new JsonMedia(
                new Error("CODE_001",
                        "Something bad happened at " + request.getUri().getPath())
        );
     
        StatusCode code = context.getStatusCode();
        ResponseBuilder builder = Response.builder()
                .withBody(new Body(media))
                .withStatusCode(code.getValue())
                .withHeader("Content-Type", "application/json");
        return builder.build();
    }
    
}
```

