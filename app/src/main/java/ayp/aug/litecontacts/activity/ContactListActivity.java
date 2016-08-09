package ayp.aug.litecontacts.activity;

import android.support.v4.app.Fragment;

import ayp.aug.litecontacts.fragment.ContactListFragment;

/**
 * Created by Tanaphon on 8/9/2016.
 */
public class ContactListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment onCreateFragment() {
        return ContactListFragment.newInstance();
    }
}
