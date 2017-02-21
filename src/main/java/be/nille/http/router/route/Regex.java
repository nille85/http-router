/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.route;

import be.nille.http.router.request.Request;
import be.nille.http.router.request.RequestMatcher;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nholvoet
 */
public class Regex implements RequestMatcher{
    
    private final String value;
    
    public Regex(final Path path){
        this(path.getValue());
    }
    
    public Regex(final String value){
        this.value = value;
    }

    @Override
    public boolean matches(Request request) {
        Pattern pattern = Pattern.compile(value);
        Matcher matcher = pattern.matcher(request.getPath().getValue());
        boolean matches = matcher.matches();
        return matches;
    }
    
}
