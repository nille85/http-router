/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router;

import com.google.common.base.Preconditions;
import lombok.Getter;

/**
 *
 * @author nholvoet
 */
@Getter
public final class Port {
    
    private final int value;
    
    public Port(final int value){
        Preconditions.checkArgument(value > 0, "The port should be bigger than zero, the submitted value was %s", value);
        this.value = value;
    }
    
}
