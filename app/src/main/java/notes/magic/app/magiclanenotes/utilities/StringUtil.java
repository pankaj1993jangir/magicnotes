package notes.magic.app.magiclanenotes.utilities;

/**
 * Created by pankaj on 7/12/17.
 */

public class StringUtil {
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().equals("") || s.trim().equalsIgnoreCase("null");
    }
}
