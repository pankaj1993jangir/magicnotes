package notes.magic.app.magiclanenotes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pankaj on 7/12/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper mInstance = null;


    public static DatabaseHelper getInstance(Context ctx) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://developer.android.com/resources/articles/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(KeyValueDataBase.getCreateQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + KeyValueDataBase.TABLE_NAME);
    }

    public DatabaseHelper(Context context) {
        super(context,
                "magicnotes", null,
                1);

    }


    @Override
    public void close() {
        super.close();
    }
}
