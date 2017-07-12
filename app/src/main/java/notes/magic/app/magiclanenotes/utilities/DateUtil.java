package notes.magic.app.magiclanenotes.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by pankaj on 7/12/17.
 */


public class DateUtil {
    //12th Jan 2016, 10:10 PM
    public static String getFormattedTime(long milli) {
        String str = "";
        try {
            if (milli > 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(milli);
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, KK:mm a", Locale.ENGLISH);
                str = sdf.format(calendar.getTime());
            }
            return str;
        } catch (Exception e) {
            return str;
        }
    }
}
