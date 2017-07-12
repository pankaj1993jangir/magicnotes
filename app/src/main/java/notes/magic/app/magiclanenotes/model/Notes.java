package notes.magic.app.magiclanenotes.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pankaj on 7/12/17.
 */

public class Notes {

    private List<Note> notes;

    public List<Note> getNotes() {
        if (notes == null) {
            notes = new ArrayList<>();
        }
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Note> removeNote(Note rNote) {
        List<Note> notes = getNotes();
        for (Note note : notes) {
            if ((note.getTitle() + note.getNote()).equals(rNote.getTitle() + rNote.getNote())) {
                notes.remove(note);
                break;
            }
        }
        return notes;
    }

    public List<Note> updateNote(Note oldNote, Note newNote) {
        List<Note> notes = getNotes();
        for (Note note : notes) {
            if ((note.getTitle() + note.getNote()).equals(oldNote.getTitle() + oldNote.getNote())) {
                notes.remove(note);
                break;
            }
        }
        notes.add(newNote);
        return notes;
    }
}
