package ayp.aug.litecontacts.model;

import ayp.aug.litecontacts.model.ContactDBSchema.ContactTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private PhoneBook(Context context) {
        this.context = context;
        ContactDBHelper contactDBHelper = new ContactDBHelper(context);
        database = contactDBHelper.getWritableDatabase();
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

    public ContactCursorWrapper queryContact(String whereCause, String[] whereArgs) {
        Cursor cursor = database.query(ContactTable.NAME,
                null,
                whereCause,
                whereArgs,
                null,
                null,
                null,
                null
        );
        return new ContactCursorWrapper(cursor);
    }

    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        ContactCursorWrapper contactCursorWrapper = queryContact(null, null);

        try {
            contactCursorWrapper.moveToFirst();
            while (!contactCursorWrapper.isAfterLast()) {
                contacts.add(contactCursorWrapper.getContact());
                contactCursorWrapper.moveToNext();
            }
        } finally {
            contactCursorWrapper.close();
        }
        return contacts;
    }

    public File getPhotoFile(String photoFileName) {
        File externalFilePictureDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilePictureDir == null)
            return null;
        return new File(externalFilePictureDir, photoFileName);
    }


    public void addContact(Contact contact) {
        ContentValues contentValues = getContentValues(contact);
        database.insert(ContactTable.NAME, null, contentValues);
    }
}