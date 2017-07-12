package notes.magic.app.magiclanenotes.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import notes.magic.app.magiclanenotes.model.Note;
import notes.magic.app.magiclanenotes.model.Notes;
import notes.magic.app.magiclanenotes.utilities.ListUtil;

/**
 * Created by pankaj on 7/12/17.
 */

public class KeyValueDataBase {


    public static String TABLE_NAME = "keyValueDatabase";
    public static String COLUMN_id = "id";
    public static String COLUMN_keytype = "keytype";
    public static String COLUMN_key = "key";
    public static String COLUMN_value = "value";
    public static String COLUMN_savedTime = "savedTime";

    public static String getCreateQuery() {
        return "CREATE TABLE " +
                TABLE_NAME +
                "(id integer primary key, " +
                "keytype text, " +
                "key text unique," +
                "savedTime integer," +
                "value text)";
    }

    public int id = -1;

    public String keytype;

    private String key;

    public String value;

    public long savedTime;

    public KeyValueDataBase(String key, String value) {
        this.id = key.hashCode();
        this.key = key;
        this.value = value;
        this.savedTime = System.currentTimeMillis();
    }


    public KeyValueDataBase() {
    }


    public String getKeytype() {
        return keytype;
    }

    public void setKeytype(String keytype) {
        this.keytype = keytype;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(long savedTime) {
        this.savedTime = savedTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static List<KeyValueDataBase> find(Context context, String query) {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            return getObjectList(cursor);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public static KeyValueDataBase getKVB(Context context, String keyName) {
        List<KeyValueDataBase> objList = find(context,
                "select * from " + TABLE_NAME + " where " + COLUMN_key +
                        " = '" + keyName.toLowerCase() + "' or " + COLUMN_key + " = '" +
                        keyName + "'");
        if (!ListUtil.isEmpty(objList)) {
            return objList.get(0);
        }
        return null;
    }

    private static List<KeyValueDataBase> getObjectList(Cursor cursor) {
        List<KeyValueDataBase> list = new ArrayList<KeyValueDataBase>();
        while (!cursor.isAfterLast()) {
            KeyValueDataBase keyValueDatabase = new KeyValueDataBase();
            keyValueDatabase.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_id)));
            keyValueDatabase.setKeytype(cursor.getString(cursor.getColumnIndex(COLUMN_keytype)));
            keyValueDatabase.setKey(cursor.getString(cursor.getColumnIndex(COLUMN_key)));
            keyValueDatabase.setValue(cursor.getString(cursor.getColumnIndex(COLUMN_value)));
            keyValueDatabase.setSavedTime(cursor.getLong(cursor.getColumnIndex(COLUMN_savedTime)));
            list.add(keyValueDatabase);
            cursor.moveToNext();
        }
        return list;
    }

    public static void saveValueFor(final Context context, final String key, final String value) {
        add(context, new KeyValueDataBase(key, value), null);
    }

    public static boolean add(Context context, KeyValueDataBase kv, SQLiteDatabase db) {
        long rowNumber = 0;
        if (db == null) {
            db = DatabaseHelper.getInstance(context).getWritableDatabase();
        }
        ContentValues contentValues = new ContentValues();
        if (kv.getId() != -1) {
            contentValues.put(COLUMN_id, kv.getId());
        }
        contentValues.put(COLUMN_keytype, kv.getKeytype());
        contentValues.put(COLUMN_key, kv.getKey());
        contentValues.put(COLUMN_value, kv.getValue());
        contentValues.put(COLUMN_savedTime, kv.getSavedTime());
        try {
            rowNumber = db.replace(TABLE_NAME, null, contentValues);
        } catch (Exception e) {

        }
        return rowNumber > 0;
    }

    public static Notes getNotes(Activity activity) {
        KeyValueDataBase keyValueDataBase = KeyValueDataBase.getKVB(activity, "NOTES");
        Notes notes = new Notes();
        List<Note> noteList = null;
        if (keyValueDataBase != null) {
            String gson = keyValueDataBase.getValue();
            noteList = new Gson().fromJson(gson, new TypeToken<List<Note>>() {
            }.getType());
        }
        if (noteList != null) {
            notes.setNotes(noteList);
        }
        return notes;
    }

    public static void addNewNote(Activity activity, Note note) {
        Notes notes = KeyValueDataBase.getNotes(activity);
        notes.getNotes().add(note);
        KeyValueDataBase.saveValueFor(activity, "NOTES", new Gson().toJson(notes.getNotes()));
    }

    public static void deleteNote(Activity activity, Note note) {
        Notes notes = KeyValueDataBase.getNotes(activity);
        KeyValueDataBase.saveValueFor(activity, "NOTES", new Gson().toJson(notes.removeNote(note)));
    }

    public static void updateNote(Activity activity, Note oldNote, Note newNote) {
        Notes notes = KeyValueDataBase.getNotes(activity);
        KeyValueDataBase.saveValueFor(activity, "NOTES", new Gson().toJson(notes.updateNote(oldNote, newNote)));
    }
}
