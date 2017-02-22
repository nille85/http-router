/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.request.Request;
import be.nille.http.router.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@ToString
@Slf4j
public class DefaultRoute {

    @Getter
    private final Method method;
    @Getter
    private final Path path;
    private final RouteCallback successHandler;

    public DefaultRoute(final String method, final String path, final RouteCallback successHandler) {
        this(new Method(method), new Path(path), successHandler);
    }

    public DefaultRoute(final Method method, final Path path, final RouteCallback successHandler) {
        this.method = method;
        this.path = path;
        this.successHandler = successHandler;
    }

    public Response execute(final Request request) {
        return successHandler.handle(request);
    }

    public boolean matchesMethod(final Method method) {
        return method.equals(this.method);
    }

    public boolean matchesResource(String requestPath) {
       
        //when query string is present
        requestPath = requestPath.split("\\?")[0];
        String pathValue = path.getValue();
        //when path variable is not at the end
        String pattern = pathValue.replaceAll(":.*/", "(.*)/");
       
        //when path variable is at the end
        pattern = pattern.replaceAll(":.*[^/]", "(.*)");
        Pattern p = Pattern.compile(pattern);

        Matcher matcher = p.matcher(requestPath);
        boolean matches = matcher.matches();

        return matches;

    }


}
