/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import be.nille.http.router.request.Request;
import be.nille.http.router.route.Method;
import be.nille.http.router.route.Path;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nholvoet
 */
public class NettyRequest implements Request {
    
    private final HttpRequest httpRequest;
    private final String httpContent;
    
    public NettyRequest(final HttpRequest httpRequest, final String httpContent){
        this.httpRequest = httpRequest;
        this.httpContent = httpContent;
    }

    @Override
    public Method getMethod() {
        return new Method(httpRequest.method().name());
    }


    @Override
    public Map<String, List<String>> getQueryParameters() {
      QueryStringDecoder queryStringDecoder = new QueryStringDecoder(httpRequest.uri());
      Map<String, List<String>> params = queryStringDecoder.parameters();
      return params;
    }


    @Override
    public Map<String, String> getHeaders() {
        HttpHeaders headers = httpRequest.headers();

        Map<String, String> copiedHeaders = new HashMap<>();
        if (!headers.isEmpty()) {
            for (Map.Entry<String, String> h : headers) {
                copiedHeaders.put(h.getKey(), h.getValue());

            }
        }
        return copiedHeaders;
    }

    @Override
    public Body getBody() {
        return new Body(httpContent);
    }

    @Override
    public Map<String, String> getPathParameters() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path getPath() {
        try {
            return new Path(new URI(httpRequest.uri()).getPath());
        } catch (URISyntaxException ex) {
            throw new RuntimeException(
                    String.format("The value %s is not a valid URI ", httpRequest.uri()),
                    ex
            );
        }
    }
    
}
