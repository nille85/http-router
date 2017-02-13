/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route;

import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author nholvoet
 */
@Getter
@ToString
public class RequestMethod {
        
    private final String methodName;
    
    public RequestMethod(final String methodName){
        this.methodName = methodName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.methodName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RequestMethod other = (RequestMethod) obj;
        if (!Objects.equals(this.methodName, other.methodName)) {
            return false;
        }
        return true;
    }
    
    
    
}
