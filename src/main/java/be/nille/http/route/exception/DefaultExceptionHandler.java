/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route.exception;

import be.nille.http.router.HttpRouterException;
import be.nille.http.router.HttpRouterException.Context;
import be.nille.http.router.response.Body;
import be.nille.http.router.response.Response;
import be.nille.http.router.response.StatusCode;
import be.nille.http.router.response.ResponseBuilder;
import be.nille.http.router.media.TextMedia;
import be.nille.http.router.request.Request;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class DefaultExceptionHandler implements ExceptionHandler {

    private static final String CONTENT_TYPE = "text/plain;charset=utf-8";
   

    @Override
    public Response handleException(HttpRouterException exception) {
        Context context = exception.getContext();
        Request request = context.getRequest();
        
        
        log.error(
                String.format("An error occurred while executing %s request to %s", 
                        request.getMethod().getName(),
                        request.getUri().toString()
                ),
                exception.getCause()
        );
        StatusCode code = context.getStatusCode();
        ResponseBuilder builder = Response.builder()
                .withBody(new Body(new TextMedia("")))
                .withStatusCode(code.getValue())
                .withHeader("Content-Type", CONTENT_TYPE);
        return builder.build();
    }

}
