/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.body;
/**
 *
 * @author nholvoet
 */
public class TextBody implements Body {

    private final String plainText;
    
    public TextBody(final String plainText){
        this.plainText = plainText;
    }
    
    @Override
    public String print() {
        return plainText;
    }

}
