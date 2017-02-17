/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.response;

import be.nille.http.router.media.Body;
import be.nille.http.router.media.JsonMedia;
import be.nille.http.router.media.TextMedia;

import static junit.framework.Assert.assertEquals;
import lombok.Getter;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class BodyTest {

    @Test
    public void testPlainText() {
        Body body = new Body(new TextMedia("this is the body"));
        assertEquals("this is the body", body.print());
    }

    @Test
    public void testJson() {

        JsonMedia media = new JsonMedia(new Name("John", "Doe"));

        Body body = new Body(media);
        assertEquals("{\"first\":\"John\",\"last\":\"Doe\"}", body.print());
    }
    
    @Getter
    private static class Name{
        private final String first;
        private final String last;
        
        public Name(final String first, final String last){
            this.first = first;
            this.last = last;
        }
    }

}
