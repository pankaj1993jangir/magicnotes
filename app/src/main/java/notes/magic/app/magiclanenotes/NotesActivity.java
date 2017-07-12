package notes.magic.app.magiclanenotes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import notes.magic.app.magiclanenotes.adapter.NotesAdapter;
import notes.magic.app.magiclanenotes.database.KeyValueDataBase;
import notes.magic.app.magiclanenotes.handler.NotesHandler;
import notes.magic.app.magiclanenotes.model.Note;
import notes.magic.app.magiclanenotes.model.Notes;
import notes.magic.app.magiclanenotes.sorter.hasPicSorter;
import notes.magic.app.magiclanenotes.sorter.titleSorter;
import notes.magic.app.magiclanenotes.utilities.ListUtil;

public class NotesActivity extends AppCompatActivity {

    private NotesHandler handler;
    private ListView notesListView;
    private NotesAdapter notesAdapter = null;
    private Notes notes = null;
    private List<Note> noteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeData();
        initializeUIElements();
        handler = new NotesHandler(this);
        handler.setFabOnClick();
    }

    private void initializeData() {
        notes = KeyValueDataBase.getNotes(this);
        noteList.clear();
        if (notes == null) {
            notes = new Notes();
        }
        noteList.addAll(ListUtil.isEmpty(notes.getNotes()) ? new ArrayList<Note>() : notes.getNotes());
    }

    private void initializeUIElements() {
        notesListView = (ListView) findViewById(R.id.lvNotes);
        notesListView.setEmptyView(findViewById(R.id.empty_list));
        notesAdapter = new NotesAdapter(this, R.layout.list_view_item_note, noteList);
        notesListView.setAdapter(notesAdapter);
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = (Note) parent.getItemAtPosition(position);
                handler.openNote(note);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.titleASC) {
            Collections.sort(noteList, new titleSorter(true));
        } else if (id == R.id.titleDESC) {
            Collections.sort(noteList, new titleSorter(false));
        } else if (id == R.id.hasPic) {
            Collections.sort(noteList, new hasPicSorter());
        }
        notesAdapter.notifyDataSetChanged();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializeData();
        notesAdapter.notifyDataSetChanged();
    }

}
