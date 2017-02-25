/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;


import be.nille.http.router.v2.request.Request;
import be.nille.http.router.v2.request.RouterRequest;
import be.nille.http.router.v2.request.RequestMatcher;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Niels Holvoet
 */
@Getter
@ToString
public class Method implements RequestMatcher{

    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String POST = "POST";
    public static final String DELETE = "DELETE";

    private final String name;

    public Method(final String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
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
        final Method other = (Method) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
   

    @Override
    public boolean matches(Request request) {
       return this.equals(request.getMethod());
    }

}
