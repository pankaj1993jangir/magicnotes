package notes.magic.app.magiclanenotes.sorter;

import java.util.Comparator;

import notes.magic.app.magiclanenotes.model.Note;

/**
 * Created by pankaj on 7/12/17.
 */

public class titleSorter implements Comparator<Note> {

    private boolean isASC;

    public titleSorter(boolean isASC) {
        this.isASC = isASC;
    }

    @Override
    public int compare(Note o1, Note o2) {
        return this.isASC ? o1.getTitle().compareTo(o2.getTitle()) : -(o1.getTitle().compareTo(o2.getTitle()));
    }
}
