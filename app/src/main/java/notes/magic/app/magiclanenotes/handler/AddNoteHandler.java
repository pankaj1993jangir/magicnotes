package notes.magic.app.magiclanenotes.handler;

import android.content.Intent;

import notes.magic.app.magiclanenotes.activity.AddNotesActivity;
import notes.magic.app.magiclanenotes.database.KeyValueDataBase;
import notes.magic.app.magiclanenotes.model.Note;
import notes.magic.app.magiclanenotes.utilities.PermissionUtil;

/**
 * Created by pankaj on 7/12/17.
 */

public class AddNoteHandler {
    private final AddNotesActivity activity;

    public AddNoteHandler(AddNotesActivity activity) {
        this.activity = activity;
    }

    public void saveNote(Note note) {
        KeyValueDataBase.addNewNote(activity, note);
        activity.finish();
    }

    public void updateNote(Note oldNote, Note newNote) {
        KeyValueDataBase.updateNote(activity, oldNote, newNote);
        activity.finish();
    }

    public void selectImage() {
        if (PermissionUtil.checkPermission(activity)) {
            galleryIntent();
        }
    }

    public void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);//
        activity.startActivityForResult(Intent.createChooser(intent, "Select File"), 222);
    }
}
