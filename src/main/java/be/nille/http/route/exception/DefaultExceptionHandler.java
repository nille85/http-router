/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route.exception;

import be.nille.http.router.HttpRouterException;
import be.nille.http.router.PathNotFoundException;
import be.nille.http.router.MethodNotFoundException;
import be.nille.http.router.media.Body;
import be.nille.http.router.response.Response;
import be.nille.http.router.response.StatusCode;
import be.nille.http.router.response.ResponseBuilder;
import be.nille.http.router.media.TextMedia;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class DefaultExceptionHandler implements ExceptionHandler {


    @Override
    public Response handleException(HttpRouterException exception) {
        log.error(exception.getMessage());
        ResponseBuilder builder = Response.builder()
                .withHeader("Content-Type", "text/plain;charset=utf-8");

        if (exception.getCause() instanceof MethodNotFoundException) {
            builder.withBody(new Body(new TextMedia("not allowed")))
                    .withStatusCode(StatusCode.METHOD_NOT_ALLOWED);

        } else if (exception.getCause() instanceof PathNotFoundException) {
            builder.withBody(new Body(new TextMedia("not found")))
                    .withStatusCode(StatusCode.NOT_FOUND);
        } else {
            builder.withBody(new Body(new TextMedia("internal server error")))
                    .withStatusCode(StatusCode.INTERNAL_SERVER_ERROR);
        }

        return builder.build();
    }

}
