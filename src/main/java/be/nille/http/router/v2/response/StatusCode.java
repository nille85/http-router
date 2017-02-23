/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.response;

import lombok.Getter;

/**
 *
 * @author nholvoet
 */
@Getter
public class StatusCode {

    public static final int OK = 200;
    public static final int BAD_REQUEST = 400;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int INTERNAL_SERVER_ERROR = 500;

    private final int value;

    public StatusCode(final int value) {
        this.value = value;
    }

}
