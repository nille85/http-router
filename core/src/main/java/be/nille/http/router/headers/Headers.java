/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.headers;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Niels Holvoet
 */
public final class Headers {
    
    private final Map<String,String> headers;
    
    public Headers(){
        this(new HashMap<>());
    }
    
    public Headers(final Map<String,String> headers){
        this.headers = headers;
    }
    
    public Headers add(final String key, final String value){
        Map<String,String> copy = headers;
        copy.put(key, value);
        return new Headers(copy);
    }
    
    public Headers remove(final String key){
        Map<String,String> copy = headers;
        copy.remove(key);
        return new Headers(copy);
    }
    
    public String getValue(final String key){
        return headers.get(key);
    }
    
    public Map<String,String> map(){
        return ImmutableMap.copyOf(headers);
    }
    
}
