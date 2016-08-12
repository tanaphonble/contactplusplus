package ayp.aug.litecontacts.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.UUID;

import ayp.aug.litecontacts.R;
import ayp.aug.litecontacts.fragment.ContactListFragment;
import ayp.aug.litecontacts.fragment.DetailContactFragment;
import ayp.aug.litecontacts.model.Contact;

/**
 * Created by Tanaphon on 8/10/2016.
 */
public class DetailContactActivity extends SingleFragmentActivity
        implements DetailContactFragment.Callbacks {

    FrameLayout paneTwoLayout;

    private static final String CONTACT_UUID = "CONTACT_UUID";
    private UUID contactUUID;

    public static Intent newIntent(Activity activity, UUID uuid) {
        Intent intent = new Intent(activity, DetailContactActivity.class);
        intent.putExtra(CONTACT_UUID, uuid);
        return intent;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_one_pane;
    }



//
//    @Override
//    protected void onResumeFragments() {
//        super.onResumeFragments();
//        try {
//            paneTwoLayout = (FrameLayout) findViewById(R.id.detail_fragment_container);
//        } catch (Exception e) {
//            paneTwoLayout = null;
//        }
//        contactUUID = (UUID) getIntent().getSerializableExtra(CONTACT_UUID);
//        if (paneTwoLayout == null) {
//        } else {
////            setDetailFragment();
////            setListFragment();
//
//        }
//    }

    @Override
    protected Fragment onCreateFragment() {

        contactUUID = (UUID) getIntent().getSerializableExtra(CONTACT_UUID);
        return DetailContactFragment.newInstance(contactUUID);
    }

    private void setDetailFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.detail_fragment_container,
                        DetailContactFragment.newInstance(contactUUID))
                .commit();
    }

    private void setListFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.detail_fragment_container,
                        ContactListFragment.newInstance())
                .commit();
    }


    @Override
    public void onContactDeleted() {
        finish();
    }

    @Override
    public void onContactUpdate(Contact contact) {
        // todo on update
    }
}
