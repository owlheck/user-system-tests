package user.system.utils;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;


public class Utils {
    public static String getResourceAsString(String Location) {
        URL url = Resources.getResource(Location);

        try {
            return Resources.toString(url, Charsets.UTF_8);
        } catch (IOException ex) {
            return "";
        }
    }
}
