package ayp.aug.litecontacts.model;

import ayp.aug.litecontacts.model.ContactDBSchema.ContactTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tanaphon on 8/9/2016.
 */
public class ContactDBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "contact.db";

    public ContactDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create database "contact" that contains columns > id(primary key), uuid, name, number and email
        sqLiteDatabase.execSQL("CREATE TABLE " + ContactTable.NAME
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ContactTable.Columns.UUID + ","
                + ContactTable.Columns.NAME + ","
                + ContactTable.Columns.NUMBER + ","
                + ContactTable.Columns.EMAIL + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}