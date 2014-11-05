package greenlab.com.UI.fragments;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import greenlab.com.Models.Recorder;
import greenlab.com.R;
import greenlab.com.UI.UpsideDownText;
import greenlab.com.event.models.TimeRecorderEvent;
import greenlab.com.task.RecordService;

public class RecordFragment extends BaseFragment implements OnClickListener {

    private TextView timeView;

    private UpsideDownText timeMillisecondView;

    private ImageButton startButton, stopButton;

    private RecordService recordService;

    private ServiceConnection recordConnection;

    private Handler handler;

    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getRecordView(inflater, container);
        initUI(view);
        initHandler();

        intent = new Intent(getActivity(), RecordService.class);
        recordConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                recordService = (((RecordService.LameBinder) binder).getService());
                recordService.setHandler(handler);

                if (recordService.getStatusRecord() == Recorder.StatusRecord.RECORD) {
                    handler.sendEmptyMessage(Recorder.MSG_REC_STARTED);
                    recordService.updateTimeUI();
                }

                if (recordService.getStatusRecord() == Recorder.StatusRecord.PAUSE_RECORD) {
                    handler.sendEmptyMessage(Recorder.MSG_REC_PAUSE);
                    recordService.updateTimeUI();
                }
            }

            public void onServiceDisconnected(ComponentName name) {
                recordService = null;
            }
        };
        bindService();

        return view;
    }

    private View getRecordView(LayoutInflater inflater, ViewGroup container) {
        if (getMainActivity().isPortraitOrientation())
            return inflater.inflate(R.layout.record_portration, container, false);
        return inflater.inflate(R.layout.record_landscape, container, false);
    }

    @Override
    protected void initUI(View view) {
        timeView = (TextView) view.findViewById(R.id.time);
        timeMillisecondView = (UpsideDownText) view.findViewById(R.id.millesecond);
        startButton = (ImageButton) view.findViewById(R.id.change);
        startButton.setOnClickListener(this);
        stopButton = (ImageButton) view.findViewById(R.id.stop);
        stopButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change:
                startService();
                bindService();
                break;
            case R.id.stop:
                try {
                    stopRecord();
                    unbindService();
                    recordService = null;
                } catch (IllegalArgumentException e) {
                    showToast(getString(R.string.error_service_unbind));
                } catch (NullPointerException e) {
                    showToast(getString(R.string.error_service_unbind));
                } catch (Exception e) {
                    showToast(getString(R.string.error_service_unbind));
                }
                break;
        }
    }

    public void initHandler() {

        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case Recorder.MSG_ERROR_AUDIO_ENCODE:
                        showToast(String.format(getString(R.string.error_code), Recorder.MSG_ERROR_AUDIO_ENCODE));
                        break;
                    case Recorder.MSG_ERROR_AUDIO_RECORD:
                        showToast(String.format(getString(R.string.error_code), Recorder.MSG_ERROR_AUDIO_RECORD));
                        break;
                    case Recorder.MSG_ERROR_CLOSE_FILE:
                        showToast(String.format(getString(R.string.error_code), Recorder.MSG_ERROR_CLOSE_FILE));
                        break;
                    case Recorder.MSG_ERROR_CREATE_FILE:
                        showToast(String.format(getString(R.string.error_code), Recorder.MSG_ERROR_CREATE_FILE));
                        break;
                    case Recorder.MSG_ERROR_GET_MIN_BUFFERSIZE:
                        showToast(String.format(getString(R.string.error_code), Recorder.MSG_ERROR_GET_MIN_BUFFERSIZE));
                        break;
                    case Recorder.MSG_ERROR_REC_START:
                        showToast(String.format(getString(R.string.error_code), Recorder.MSG_ERROR_REC_START));
                        break;
                    case Recorder.MSG_ERROR_WRITE_FILE:
                        showToast(String.format(getString(R.string.error_code), Recorder.MSG_ERROR_WRITE_FILE));
                        break;
                    case Recorder.MSG_REC_PAUSE:
                        startButton.setBackgroundResource(R.drawable.btn_play_selector);
                        break;
                    case Recorder.MSG_REC_STARTED:
                        startButton.setBackgroundResource(R.drawable.btn_pause_selector);
                        break;
                    case Recorder.MSG_REC_STOPPED:
                        timeView.setText(getString(R.string.startTime));
                        timeMillisecondView.setText(getString(R.string.startTimeMillisecond));
                        startButton.setBackgroundResource(R.drawable.btn_play_selector);
                        getMainActivity().openDialogActivity();
                        break;
                    case Recorder.MSG_UPDATE_TIME:
                        timeView.setText(((TimeRecorderEvent) msg.obj).time);
                        timeMillisecondView.setText(((TimeRecorderEvent) msg.obj).millisecond);
                        break;
                }
            }

            ;
        };
    }

    public void startService() {
        getActivity().startService(intent);
    }

    public void bindService() {
        getActivity().bindService(intent, recordConnection, 0);
    }

    public void unbindService() {
        getActivity().unbindService(recordConnection);
    }

    public void stopRecord() {
        recordService.stopRecord();
    }

}
