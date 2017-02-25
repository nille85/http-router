/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;


import be.nille.http.router.v2.request.Request;
import be.nille.http.router.v2.request.RouterRequest;
import be.nille.http.router.v2.request.RequestMatcher;
import be.nille.http.router.v2.request.VariableContainer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nholvoet
 */
public class Regex implements RequestMatcher, VariableContainer{
    
    private final String value;
    
    
    public Regex(final String value){
        this.value = value;
    }

    @Override
    public boolean matches(final Request request) {
        Pattern pattern = Pattern.compile(value);
        Matcher matcher = pattern.matcher(request.getPath());
        boolean matches = matcher.matches();
        return matches;
    }

    @Override
    public List<String> variables(final Request request) {
        List<String> variables = new ArrayList<>();
        Pattern pattern = Pattern.compile(value);
        Matcher matcher = pattern.matcher(request.getPath());
        
        if(matcher.matches()){
            int groupCount = matcher.groupCount();
            if(groupCount > 1){
          
                for (int i = 1; i <= groupCount; i++) {
                    variables.add(matcher.group(i));
                }
            }
        }
        return variables;
    }
    
}
