package ayp.aug.litecontacts.model;

import ayp.aug.litecontacts.model.ContactDBSchema.ContactTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tanaphon on 8/9/2016.
 */
public class PhoneBook {

    // static variables
    private static PhoneBook instance;

    // variables
    private Context context;
    private SQLiteDatabase database;

    //---- public zone ----//

    // singleton
    public static PhoneBook getInstance(Context context) {
        if (instance == null)
            instance = new PhoneBook(context);
        return instance;
    }

    //---- private zone ----//

    // constructor
    private PhoneBook(Context context){
        this.context = context;
        database = new ContactDBHelper(context).getWritableDatabase();
    }

    // ContentValue for query
    private static ContentValues getContentValues(Contact contact) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactTable.Columns.UUID, contact.getUuid().toString());
        contentValues.put(ContactTable.Columns.NAME, contact.getName());
        contentValues.put(ContactTable.Columns.NUMBER, contact.getNumber());
        contentValues.put(ContactTable.Columns.EMAIL, contact.getEmail());
        return contentValues;
    }


}
