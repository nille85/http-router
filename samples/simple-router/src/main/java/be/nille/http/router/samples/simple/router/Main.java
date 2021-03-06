/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.samples.simple.router;

import be.nille.http.router.body.TextBody;
import be.nille.http.router.request.Method;
import be.nille.http.router.response.RouteResponse;
import be.nille.http.router.response.StatusCode;
import be.nille.http.router.route.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import be.nille.http.router.netty.HttpRouter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Niels Holvoet
 */
@Slf4j
public class Main {

    @Getter
    public static class Person {

        private final String firstName;
        private final String lastName;

        public Person(final String firstName, final String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    public static void main(final String[] args) throws Exception {

        List<Route> routes = new ArrayList<>();

        Route fileRoute = new StaticFileRoute("/static","submap");
        routes.add(fileRoute);

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

        Route pathMethodRoute = new PathRoute(
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
    }

}
