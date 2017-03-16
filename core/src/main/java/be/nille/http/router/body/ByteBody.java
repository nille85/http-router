/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.body;

/**
 *
 * @author Niels Holvoet
 */
public class ByteBody implements Body {
    
    private final byte[] bytes;
    
    public ByteBody(final byte[] bytes){
        this.bytes = bytes;
    }

    @Override
    public byte[] bytes() {
        return bytes;
    }
    
}
