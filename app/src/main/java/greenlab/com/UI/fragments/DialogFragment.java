package greenlab.com.UI.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import greenlab.com.R;

/**
 * Created by Metrolog on 28.10.14.
 */

public class DialogFragment extends BaseFragment implements View.OnClickListener {

    private EditText mailValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mail_dialog, container, false);
        initUI(view);
        return view;
    }

    @Override
    protected void initUI(View view) {
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        Button send = (Button) view.findViewById(R.id.send);
        send.setOnClickListener(this);

        mailValue = (EditText) view.findViewById(R.id.mail);
    }

    @Override
    public void onClick(View view) {
        getMainActivity().openRecordFragment();
        switch (view.getId()) {
            case R.id.cancel:
                break;
            case R.id.send:
                getMainActivity().sendRecord(mailValue.getText().toString());
                break;
        }
    }


}
