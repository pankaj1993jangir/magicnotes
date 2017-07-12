package notes.magic.app.magiclanenotes.utilities;

import java.util.Collection;

/**
 * Created by pankaj on 7/12/17.
 */

public class ListUtil {
    public static <T> boolean isEmpty(Collection<T> l) {
        return l == null || l.size() == 0;
    }
}
