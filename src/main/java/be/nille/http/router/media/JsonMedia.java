/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.media;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 *
 * @author nholvoet
 */
public class JsonMedia implements Media {

    private static final ObjectMapper mapper = new ObjectMapper();
    
    private final Object object;
    
    public JsonMedia(final Object object){
        this.object = object;
    }


    
    @Override
    public String print() {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
           throw new RuntimeException(
                   String.format("Could not create json"), 
                   ex
           );
        }
    }

}
