package ayp.aug.litecontacts.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

import ayp.aug.litecontacts.fragment.DetailContactFragment;
import ayp.aug.litecontacts.model.Contact;

/**
 * Created by Tanaphon on 8/10/2016.
 */
public class DetailContactActivity extends SingleFragmentActivity {

    private static final String CONTACT_UUID = "CONTACT_UUID";

    public Intent newIntent(Activity activity, UUID uuid) {
        Intent intent = new Intent(activity, DetailContactActivity.class);
        intent.putExtra(CONTACT_UUID, uuid);
        return intent;
    }

    @Override
    protected Fragment onCreateFragment() {
        UUID contactUUID = (UUID) getIntent().getSerializableExtra(CONTACT_UUID);
        return DetailContactFragment.newInstance(contactUUID);
    }
}
