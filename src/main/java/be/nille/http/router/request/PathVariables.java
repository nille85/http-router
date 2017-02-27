/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.request;

import be.nille.http.router.headers.*;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Niels Holvoet
 */
public final class PathVariables {
    
    private final Map<String,String> variables;
    
    public PathVariables(){
        this(new HashMap<>());
    }
    
    public PathVariables(final Map<String,String> variables){
        this.variables = variables;
    }
    
    
    public String get(final String key){
        return variables.get(key);
    }
    
    public Map<String,String> map(){
        return ImmutableMap.copyOf(variables);
    }
    
}
