package greenlab.com.UI.fragments;

import android.app.Fragment;
import android.view.View;
import android.widget.Toast;

import greenlab.com.UI.activity.MainActivity;

/**
 * Created by Metrolog on 28.10.14.
 */

public abstract class BaseFragment extends Fragment {

    protected final MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    protected void showToast(String message) {
        Toast.makeText(getActivity(), message,
                Toast.LENGTH_SHORT).show();
    }

    protected abstract void initUI(View view);
}
