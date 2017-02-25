/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.http.router.v2.route;



import be.nille.http.router.v2.request.Request;
import be.nille.http.router.v2.request.RequestMatcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Niels Holvoet
 */
public class PathWithParams implements RequestMatcher{
    
    private final String path;
    
    public PathWithParams(final String path){
        this.path = path;
    }
    

    public Map<String,String> getPathParameters(Request request){
       
        Map<String,String> pathParams = new HashMap<>();
        List<String> keys = getKeys();
        if(!keys.isEmpty()){
            List<String> values = getValues(request);

            for(int i=0; i<keys.size();i++){
                String key = keys.get(i);
                String value = values.get(i);
                pathParams.put(key, value);
            }
        }
        
        return pathParams;
    }
    
     private List<String> getKeys(){
        
        final String pathValue = path;
         
        Pattern pattern = Pattern.compile(":([^:/]*)/?");
        Matcher matcher = pattern.matcher(pathValue);
        List<String> keys = new ArrayList<>();
        while (matcher.find()) {
            String variable = matcher.group(1);
            keys.add(variable);
        }
        return keys;
     }

    private List<String> getValues(Request request) {
        List<String> values = new ArrayList<>();
        final String pathValue = path;
        String pathRegex = pathValue.replaceAll(":([^:/]*)", "(.*)");
      
        Pattern pattern = Pattern.compile(pathRegex);
        final String requestPath = request.getPath();
        Matcher matcher = pattern.matcher(requestPath);
        if(matcher.matches()){
            for(int i=1; i<=matcher.groupCount();i++){
                values.add(matcher.group(i));
            }
        }
        return values;
    }

    @Override
    public boolean matches(Request request) {
        
        //when query string is present
        String requestPath = request.getPath().split("\\?")[0];
        String pathValue = path;
        //when path variable is not at the end
        String pattern = pathValue.replaceAll(":.*/", "(.*)/");
       
        //when path variable is at the end
        pattern = pattern.replaceAll(":.*[^/]", "(.*)");
        Pattern p = Pattern.compile(pattern);

        Matcher matcher = p.matcher(requestPath);
        boolean matches = matcher.matches();

        return matches;
    }
}
