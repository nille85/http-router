/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.request.PathVariables;
import be.nille.http.router.request.Request;
import be.nille.http.router.request.RouteRequest;
import be.nille.http.router.response.EmptyResponse;
import be.nille.http.router.response.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Slf4j
public class RegexRoute implements Route {

    private final String regex;
    private final Route origin;

    public RegexRoute(final String regex, final Route route) {
        this.regex = regex;
        this.origin = route;
    }

    @Override
    public Response response(Request request) {
        if (matches(request)) {
            return origin.response(
                    new RouteRequest(
                            request,
                            new PathVariables(getVariablesMap(request))
                    )
            );
        }
        return new EmptyResponse();
    }

    private boolean matches(final Request request) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request.getPath());
        boolean matches = matcher.matches();
        return matches;
    }

    private Map<String, String> getVariablesMap(final Request request) {
        Map<String, String> variables = new HashMap<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request.getPath());

        if(matcher.groupCount() > 0){
            int groupIndex = 1;
            while (matcher.find()) {
                log.debug("count:" + matcher.groupCount());
                variables.put(String.valueOf(groupIndex), matcher.group(groupIndex));
                groupIndex++;
            }
        }
        return variables;
    }

}
