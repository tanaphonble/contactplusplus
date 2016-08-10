package ayp.aug.litecontacts.model;

import ayp.aug.litecontacts.model.ContactDBSchema.ContactTable;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

/**
 * Created by Tanaphon on 8/10/2016.
 */
public class ContactCursorWrapper extends CursorWrapper {
    public ContactCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Contact getContact(){
        String uuid = getString(getColumnIndex(ContactTable.Columns.UUID));
        String name = getString(getColumnIndex(ContactTable.Columns.NAME));
        String number = getString(getColumnIndex(ContactTable.Columns.NUMBER));
        String email = getString(getColumnIndex(ContactTable.Columns.EMAIL));

        Contact contact = new Contact(UUID.fromString(uuid));
        contact.setName(name);
        contact.setNumber(number);
        contact.setEmail(email);
        return contact;
    }
}
