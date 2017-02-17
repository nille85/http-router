/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.media;
/**
 *
 * @author nholvoet
 */
public class TextMedia implements Media {

    private final String plainText;
    
    public TextMedia(final String plainText){
        this.plainText = plainText;
    }
    
    @Override
    public String print() {
        return plainText;
    }

}
