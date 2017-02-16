# HTTP Router
A non blocking object-oriented HTTP server based on Netty


##Initializing a Router
```
HttpRouter router = new HttpRouter()
                .listenTo(8080);
        
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



router.start();
```

Once the router is started, no more routes can be added

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

##Java 8

It is also possible to implement Request Handlers using lambda

```    
    router.addRoute(new Route(
                Method.GET,
                "/subscriptions",
                (request) -> Response.builder().withBody("fetched subscriptions").build()
        ));
```



