/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.media;

import be.nille.http.router.media.Media;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author nholvoet
 */
@Getter
@ToString
public class Body {

    private final Media media;

    public Body(final Media media) {
        this.media = media;
    }
    
    public String print(){
        return media.print();
    }
    

}
