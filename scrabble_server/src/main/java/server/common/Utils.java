package server.common;

import java.util.List;

/** 
 * @author Romain <ro.foncier@gmail.com>
 */
public class Utils {
    
    public static String arrayToJSON(List<String> data) {
        String result = "[";
        for (int i = 0; i < data.size(); i++) {
            result += "\""+data.get(i)+"\"";
            result += (i < data.size() - 1) ? ", " : "";
        }
        result += "]";
        return result;
    }
}