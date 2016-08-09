package ayp.aug.litecontacts.activity;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ayp.aug.litecontacts.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    @LayoutRes
    private int getLayoutResId() {
        return R.layout.activity_well_fit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
    }

    protected abstract Fragment onCreatFragment();

}