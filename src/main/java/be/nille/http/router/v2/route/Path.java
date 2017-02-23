/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;

import be.nille.http.router.request.Request;
import be.nille.http.router.request.RequestMatcher;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Getter
@ToString
@Slf4j
public class Path implements RequestMatcher {

    private final String value;

    public Path(final String value) {
        this.value = value;
    }

    @Override
    public boolean matches(Request request) {
        String requestPath = request.getPath().getValue();
        return value.equals(requestPath);
    }

}
