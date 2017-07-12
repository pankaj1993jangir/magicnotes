package notes.magic.app.magiclanenotes.handler;

import android.content.Intent;

import com.google.gson.Gson;

import notes.magic.app.magiclanenotes.activity.AddNotesActivity;
import notes.magic.app.magiclanenotes.activity.DisplayNotesActivity;
import notes.magic.app.magiclanenotes.database.KeyValueDataBase;
import notes.magic.app.magiclanenotes.model.Note;

/**
 * Created by pankaj on 7/12/17.
 */

public class DisplayNoteHandler {
    private final DisplayNotesActivity activity;

    public DisplayNoteHandler(DisplayNotesActivity activity) {
        this.activity = activity;
    }

    public void deleteNote(Note note) {
        KeyValueDataBase.deleteNote(activity, note);
        activity.finish();
    }

    public void editNote(Note note) {
        Intent intent = new Intent(activity, AddNotesActivity.class);
        intent.putExtra("NOTE", new Gson().toJson(note));
        activity.startActivity(intent);
        activity.finish();
    }
}
