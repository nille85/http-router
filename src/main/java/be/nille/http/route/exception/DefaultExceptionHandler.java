/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route.exception;

import be.nille.http.router.HttpClientException;
import be.nille.http.router.PathNotFoundException;
import be.nille.http.router.MethodNotFoundException;
import be.nille.http.router.response.Response;
import be.nille.http.router.response.Response.StatusCode;
import be.nille.http.router.response.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class DefaultExceptionHandler implements ExceptionHandler {


    @Override
    public Response handleException(HttpClientException exception) {
        log.error(exception.getMessage());
        ResponseBuilder builder = Response.builder()
                .withHeader("Content-Type", "text/html;charset=utf-8");

        if (exception.getCause() instanceof MethodNotFoundException) {
            builder.withBody("not allowed")
                    .withStatusCode(StatusCode.METHOD_NOT_ALLOWED);

        } else if (exception.getCause() instanceof PathNotFoundException) {
            builder.withBody("not found")
                    .withStatusCode(StatusCode.NOT_FOUND);
        } else {
            builder.withBody("server error")
                    .withStatusCode(StatusCode.INTERNAL_SERVER_ERROR);
        }

        return builder.build();
    }

}
