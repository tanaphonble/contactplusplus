package ayp.aug.litecontacts.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import ayp.aug.litecontacts.R;
import ayp.aug.litecontacts.fragment.ContactListFragment;
import ayp.aug.litecontacts.fragment.DetailContactFragment;
import ayp.aug.litecontacts.model.Contact;

/**
 * Created by Tanaphon on 8/9/2016.
 */
public class ContactListActivity extends SingleFragmentActivity implements ContactListFragment.Callbacks{


    
    @Override
    protected Fragment onCreateFragment() {
        return ContactListFragment.newInstance();
    }


    @Override
    public void onContactSelected(Contact contact) {
        if(findViewById(R.id.detail_fragment_container) == null){
            Intent intent = DetailContactActivity.newIntent(this, contact.getUuid());
            startActivity(intent);
        }else{
            Fragment detailContactFragment = DetailContactFragment.newInstance(contact.getUuid());

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_fragment_container, detailContactFragment)
                    .commit();
        }
    }
}
