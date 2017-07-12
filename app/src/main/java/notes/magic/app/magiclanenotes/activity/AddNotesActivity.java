package notes.magic.app.magiclanenotes.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import notes.magic.app.magiclanenotes.R;
import notes.magic.app.magiclanenotes.handler.AddNoteHandler;
import notes.magic.app.magiclanenotes.model.Note;
import notes.magic.app.magiclanenotes.utilities.PermissionUtil;
import notes.magic.app.magiclanenotes.utilities.StringUtil;

public class AddNotesActivity extends AppCompatActivity {

    private AddNoteHandler handler;

    private EditText etTitle;
    private EditText etNote;

    private ImageView imageView;
    private TextView tvSelectImg;

    private Button btSave;
    private Note oldNote = null;
    private Note newNote = null;
    private String imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        handler = new AddNoteHandler(this);

        initializeDataFromIntent();
        initializeUIElements();
        settOnClickListner();
    }

    private void initializeUIElements() {
        tvSelectImg = (TextView) findViewById(R.id.tvSelectImg);
        imageView = (ImageView) findViewById(R.id.imageNote);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etNote = (EditText) findViewById(R.id.etNote);
        btSave = (Button) findViewById(R.id.btSave);
        if (oldNote != null) {
            etTitle.setText(oldNote.getTitle());
            etNote.setText(oldNote.getNote());
            Bitmap bitm = null;
            if (oldNote.getImgUrl() != null) {
                try {
                    imgUri = oldNote.getImgUrl();
                    bitm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(oldNote.getImgUrl()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            imageView.setImageBitmap(bitm);
        }
        tvSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.selectImage();
            }
        });
    }

    private void initializeDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            oldNote = new Gson().fromJson(bundle.getString("NOTE"), Note.class);
        }
    }

    private void settOnClickListner() {
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNote = validation();
                if (newNote != null) {
                    newNote.setImgUrl(imgUri);
                    if (oldNote != null) {
                        handler.updateNote(oldNote, newNote);
                    } else {
                        handler.saveNote(newNote);
                    }
                }
            }
        });
    }

    private Note validation() {
        Note note = null;
        String title = etTitle.getText().toString();
        String noteText = etNote.getText().toString();

        boolean isValidate = true;

        if (StringUtil.isNullOrEmpty(title)) {
            isValidate = false;
            etTitle.setError(getString(R.string.title_require));
        }
        if (StringUtil.isNullOrEmpty(noteText)) {
            isValidate = false;
            etNote.setError(getString(R.string.note_reuire));
        }

        if (isValidate) {
            if (oldNote != null) {
                note = new Note(title, noteText, oldNote.getCreationTime());
            } else {
                note = new Note(title, noteText);
            }
        }
        return note;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtil.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    handler.galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 222) {
                onSelectFromGalleryResult(data);
            }
        }
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(data.getData().toString()));
                imgUri = data.getData().toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        imageView.setImageBitmap(bm);
    }
}
