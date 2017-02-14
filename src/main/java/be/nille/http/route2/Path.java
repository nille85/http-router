/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.route2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nholvoet
 */
@Getter
@ToString
@Slf4j
public class Path {
    
    private final String value;
    
    public Path(final String value){
        this.value = value;
    }
    
    public boolean matches(final String requestPath){
        String valueWithWildcards = value.replaceAll("\\{.*\\}", "(.*)");
        String patternString =  "^" + valueWithWildcards + "(\\?.*)?$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(requestPath);
        
        boolean matches = matcher.matches();
        return matches;
    }
    
    public List<String> getVariables(){
        List<String> variables = new ArrayList<>();       
        Pattern pattern = Pattern.compile("\\{([^\\{\\}]*)\\}");
        Matcher matcher = pattern.matcher(value);
        while (matcher.find()) {
            variables.add(matcher.group(1));
        }        
        return variables;
    }

   
    
    
    
}
