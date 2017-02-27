/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import java.nio.charset.Charset;
import lombok.Getter;

/**
 *
 * @author Niels Holvoet
 */
@Getter
public final class HttpMetaData {

    private final boolean keepAlive;
    private final Charset charset;

    public HttpMetaData(final boolean keepAlive, final Charset charset) {
        this.keepAlive = keepAlive;
        this.charset = charset;
    }

}
