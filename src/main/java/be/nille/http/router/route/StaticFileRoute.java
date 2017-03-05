/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.body.ByteBody;
import be.nille.http.router.body.TextBody;
import be.nille.http.router.headers.Headers;
import be.nille.http.router.request.Method;
import be.nille.http.router.request.Request;
import be.nille.http.router.response.Response;
import be.nille.http.router.response.RouteResponse;
import be.nille.http.router.response.StatusCode;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.activation.MimetypesFileTypeMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Niels Holvoet
 */
@Slf4j
public class StaticFileRoute implements Route {

    private final String path;
    private final String resourceFolder;

    public StaticFileRoute(final String path) {
        this(path, "/");
    }

    public StaticFileRoute(final String path, final String resourceFolder) {
        this.path = path;
        this.resourceFolder = "/" + resourceFolder;
    }

    @Override
    public Response response(Request request) {

        Route route = new MethodRoute(
                new Method(
                        Method.GET), 
                        new RegexRoute(
                                "^"+ path + "(.*)$",
                                (r) -> {
                                    String fileLocation = r.variables().get("1");
                                    try {
                                        String location = resourceFolder + File.separatorChar + fileLocation;
                                        log.debug("location:" + location);
                                        InputStream is = this.getClass().getResourceAsStream(location);

                                        if (is != null) {
                                            byte[] bytes = IOUtils.toByteArray(is);
                                            MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
                                            return new RouteResponse(new StatusCode(200), new ByteBody(bytes), new Headers().add("content-type", mimeTypesMap.getContentType(request.getPath())));
                                        } else {
                                            return new RouteResponse(new StatusCode(404), new TextBody(""), new Headers().add("content-type", "text/plain"));
                                        }
                                    } catch (IOException ex) {
                                        return new RouteResponse(new StatusCode(500), new TextBody(ex.getMessage()), new Headers().add("content-type", "text/plain"));
                                    }
                                }
                        )
        );

        return route.response(request);

    }
}
