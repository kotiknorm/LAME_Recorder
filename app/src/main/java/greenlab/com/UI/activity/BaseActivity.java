package greenlab.com.UI.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;

import greenlab.com.R;
import greenlab.com.utils.Constants;

/**
 * Created by Metrolog on 27.10.14.
 */

public abstract class BaseActivity extends Activity {

    public boolean isPortraitOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return true;
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return false;
        else
            return false;
    }

    public void sendRecord(String mail) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("application/x-vcard");
        sharingIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { mail });
        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + Constants.fileForRecord));
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.send_lame)));

    }
}
