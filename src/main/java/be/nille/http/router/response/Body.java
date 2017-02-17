/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.response;

import be.nille.http.router.media.Media;
import be.nille.http.router.media.MediaPrinter;
import be.nille.http.router.media.TextMedia;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author nholvoet
 */
@Getter
@ToString
public class Body implements MediaPrinter {

    private final Media media;

    public Body(final Media media) {
        this.media = media;
    }
    
    public Body(final String value){
        this(new TextMedia(value));
    }
    
    @Override
    public String print(){
        return media.print();
    }
    

}
