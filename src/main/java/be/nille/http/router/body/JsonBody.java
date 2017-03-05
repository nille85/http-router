/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.body;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 *
 * @author nholvoet
 */
public class JsonBody implements Body {

    private static final ObjectMapper mapper = new ObjectMapper();
    
    private final Object object;
    
    public JsonBody(final Object object){
        this.object = object;
    }


    
    @Override
    public byte[] bytes() {
        try {
            return mapper.writeValueAsString(object).getBytes();
        } catch (JsonProcessingException ex) {
           throw new RuntimeException(
                   String.format("Could not create json"), 
                   ex
           );
        }
    }

}
