package notes.magic.app.magiclanenotes.handler;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.google.gson.Gson;

import notes.magic.app.magiclanenotes.NotesActivity;
import notes.magic.app.magiclanenotes.R;
import notes.magic.app.magiclanenotes.activity.AddNotesActivity;
import notes.magic.app.magiclanenotes.activity.DisplayNotesActivity;
import notes.magic.app.magiclanenotes.model.Note;

/**
 * Created by pankaj on 7/12/17.
 */

public class NotesHandler {
    private final NotesActivity activity;

    public NotesHandler(NotesActivity activity) {
        this.activity = activity;
    }

    public void setFabOnClick() {
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddNotesActivity();
            }
        });
    }

    private void startAddNotesActivity() {
        Intent intent = new Intent(activity, AddNotesActivity.class);
        activity.startActivity(intent);
    }

    public void openNote(Note note) {
        Intent intent = new Intent(activity, DisplayNotesActivity.class);
        intent.putExtra("NOTE", new Gson().toJson(note));
        activity.startActivity(intent);
    }
}
