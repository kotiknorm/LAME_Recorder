package greenlab.com.task;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.widget.RemoteViews;

import greenlab.com.Models.Recorder;
import greenlab.com.Models.StopWatch;
import greenlab.com.R;
import greenlab.com.UI.activity.MainActivity;
import greenlab.com.event.models.onEventService;
import greenlab.com.utils.Constants;

/**
 * Created by Metrolog on 25.10.14.
 */

public class RecordService extends Service implements onEventService {

    private Recorder recorder;

    private Notification notification;

    private StopWatch stopWatch;

    private LameBinder lameBinder = new LameBinder();

    private RemoteViews contentView;

    public Recorder.StatusRecord getStatusRecord(){
        return recorder.statusRecord;
    }

    public void onCreate() {
        super.onCreate();
        recorder = new Recorder(Constants.fileForRecord);
        stopWatch = new StopWatch();
        stopWatch.setOnEventService(this);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return lameBinder;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        changeStateRecord();

        if (recorder.statusRecord == Recorder.StatusRecord.RECORD) {
            startForeground(1, getNotification());
            stopWatch.start();
        } else if (recorder.statusRecord == Recorder.StatusRecord.PAUSE_RECORD) {
            stopWatch.pause();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void changeStateRecord() {
        recorder.record();
    }

    public void stopRecord() {
        stopWatch.stop();
        recorder.stop();
        stopForeground(true);
    }

    public void updateTimeUI(){
        stopWatch.sendTimePause();
    }

    private Notification getNotification() {
        if (notification == null) {
            notification = new Notification(R.drawable.icon, getString(R.string.notification_record_start), System.currentTimeMillis());
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
            notification.setLatestEventInfo(this, getString(R.string.app_name), getString(R.string.notification_text), pIntent);
            contentView = new RemoteViews(getApplication().getPackageName(), R.layout.notification_popup);
            notification.contentView = contentView;
        }
        return notification;
    }

    private void updateNotificationTime(String newTime) {
        if (contentView != null) {
            contentView.setTextViewText(R.id.time, getString(R.string.notification_time) + " " + newTime);
            startForeground(1, getNotification());
        }
    }

    @Override
    public void updateTime(String time) {
        updateNotificationTime(time);
    }

    public class LameBinder extends Binder {
        public RecordService getService() {
            return RecordService.this;
        }
    }

    public void setHandler(Handler handler) {
        recorder.setHandler(handler);
        stopWatch.setHandler(handler);
    }

}