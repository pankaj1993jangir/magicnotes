package notes.magic.app.magiclanenotes.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import notes.magic.app.magiclanenotes.R;
import notes.magic.app.magiclanenotes.handler.DisplayNoteHandler;
import notes.magic.app.magiclanenotes.model.Note;
import notes.magic.app.magiclanenotes.utilities.PermissionUtil;

public class DisplayNotesActivity extends AppCompatActivity {

    private Button delete;
    private Button edit;

    private TextView tvTitle;
    private TextView tvNote;

    private ImageView imageView;

    private Note note;
    public DisplayNoteHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notes);
        handler = new DisplayNoteHandler(this);
        initializedDataFromIntent();
        initializedUIElemet();
    }

    private void initializedDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        note = new Gson().fromJson(bundle.getString("NOTE"), Note.class);
    }

    private void initializedUIElemet() {
        delete = (Button) findViewById(R.id.btDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.deleteNote(note);
            }
        });

        edit = (Button) findViewById(R.id.btEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.editNote(note);
            }
        });

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(note.getTitle());

        tvNote = (TextView) findViewById(R.id.tvNote);
        tvNote.setText(note.getNote());

        imageView = (ImageView) findViewById(R.id.imageNote);

        if (PermissionUtil.checkPermission(this)) {
            Bitmap bm = null;
            if (note.getImgUrl() != null) {
                try {
                    bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(note.getImgUrl()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            imageView.setImageBitmap(bm);
        }
    }
}
