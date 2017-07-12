package notes.magic.app.magiclanenotes.sorter;

import java.util.Comparator;

import notes.magic.app.magiclanenotes.model.Note;
import notes.magic.app.magiclanenotes.utilities.StringUtil;

/**
 * Created by pankaj on 7/12/17.
 */

public class hasPicSorter implements Comparator<Note> {
    @Override
    public int compare(Note o1, Note o2) {
        boolean isleftHas = !StringUtil.isNullOrEmpty(o1.getImgUrl());
        boolean isRightHas = !StringUtil.isNullOrEmpty(o2.getImgUrl());

        if (!isleftHas && !isRightHas) {
            return 0;
        }
        if (isleftHas && isRightHas) {
            return 0;
        }
        return !isleftHas && isRightHas ? 1 : -1;
    }
}
