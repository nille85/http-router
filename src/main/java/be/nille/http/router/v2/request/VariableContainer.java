/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.request;

import java.util.List;

/**
 *
 * @author Niels Holvoet
 */
public interface VariableContainer {
    
    List<String> variables(Request request);
    
}
