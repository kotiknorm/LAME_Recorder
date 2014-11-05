package greenlab.com.UI.activity;

import android.app.FragmentManager;
import android.os.Bundle;

import greenlab.com.R;
import greenlab.com.UI.fragments.DialogFragment;
import greenlab.com.UI.fragments.RecordFragment;

/**
 * Created by Metrolog on 11.10.14.
 */

public class MainActivity extends BaseActivity {

    private final int RECORD_FRAGMENT = 1;
    private final int MAIL_FRAGMENT = 2;

    private int currentFragment;

    RecordFragment recordFragment = new RecordFragment();
    DialogFragment dialogFragment = new DialogFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        currentFragment = RECORD_FRAGMENT;
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.field_for_record, recordFragment)
                .commit();
    }

    public void openDialogActivity() {
        currentFragment = MAIL_FRAGMENT;
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left_new_fr, R.animator.slide_in_left_old_fr)
                .replace(R.id.field_for_record, dialogFragment)
                .commit();
    }

    public void openRecordFragment() {
        currentFragment = RECORD_FRAGMENT;
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_right_new_fr, R.animator.slide_in_right_old_fr)
                .replace(R.id.field_for_record, recordFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (currentFragment == RECORD_FRAGMENT) {
            super.onBackPressed();
        } else {
            openRecordFragment();
        }
    }


}
