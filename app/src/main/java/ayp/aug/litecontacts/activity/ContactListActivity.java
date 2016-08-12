package ayp.aug.litecontacts.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.UUID;

import ayp.aug.litecontacts.R;
import ayp.aug.litecontacts.fragment.ContactListFragment;
import ayp.aug.litecontacts.fragment.DetailContactFragment;
import ayp.aug.litecontacts.model.Contact;
import ayp.aug.litecontacts.model.PhoneBook;

/**
 * Created by Tanaphon on 8/9/2016.
 */
public class ContactListActivity extends SingleFragmentActivity
        implements ContactListFragment.Callbacks, DetailContactFragment.Callbacks {

    private static final int REQUEST_SELECT_CONTACT = 8;

    private Contact contact;

    @Override
    protected Fragment onCreateFragment() {
        return ContactListFragment.newInstance();
    }

    @Override
    public void onContactSelected(Contact contact) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = DetailContactActivity.newIntent(this, contact.getUuid());
//            startActivity(intent);
            this.contact = contact;
            startActivityForResult(intent, REQUEST_SELECT_CONTACT);
        } else {
            Fragment detailContactFragment = DetailContactFragment.newInstance(contact.getUuid());

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_fragment_container, detailContactFragment)
                    .commit();
        }
    }


    @Override
    public void onContactDeleted() {
        ContactListFragment contactListFragment = (ContactListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        DetailContactFragment detailContactFragment =
                (DetailContactFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.detail_fragment_container);

        getSupportFragmentManager()
                .beginTransaction()
                .detach(detailContactFragment)
                .commit();

        contactListFragment.updateListUI();

    }

    @Override
    public void onContactUpdate(Contact contact) {
        ContactListFragment contactListFragment =
                (ContactListFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);

        contactListFragment.updateListUI();
    }
}
