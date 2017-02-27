/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.request;

import com.google.common.collect.ImmutableList;
import io.netty.handler.codec.http.QueryStringDecoder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Niels Holvoet
 */
public final class QueryParameters {
    
    private final QueryStringDecoder decoder;
   
    
    public QueryParameters(final URI uri){
        this.decoder = new QueryStringDecoder(uri);     
    }
    
   
    public Map<String,List<String>> map(){
      Map<String, List<String>> params = decoder.parameters();
      return params;
    }
    
    public List<String> list(final String key){
        List list =  map().get(key);
        if(list != null){
            return list;
        }
        return new ArrayList<>();
    }
    
    public Optional<String> first(final String key){
         List<String> list = list(key);
         if(!list.isEmpty()){
             return Optional.of(list.get(0));
         }
         return Optional.empty();
    }
    
   
    
}
