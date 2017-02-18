/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.netty;

import lombok.Getter;

/**
 *
 * @author Niels Holvoet
 */
@Getter
public class TCPConnection {
    
    private final boolean keepAlive;
    
    public TCPConnection(final boolean keepAlive){
        this.keepAlive = keepAlive;
    }
    
}
