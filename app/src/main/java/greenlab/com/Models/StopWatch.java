package greenlab.com.Models;

import android.os.Handler;
import android.os.Message;

import greenlab.com.event.models.TimeRecorderEvent;
import greenlab.com.event.models.onEventService;
import greenlab.com.utils.FormatFromInterval;

/**
 * Created by Metrolog on 11.10.14.
 */

public class StopWatch {

    private long startTime;

    private boolean isWork = false;

    private long tempTime;

    private final int PERIOD_UPDATE = 234;

    private FormatFromInterval format;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private Handler handler;

    public void setOnEventService(onEventService onEventService) {
        this.onEventService = onEventService;
    }

    private onEventService onEventService;

    public StopWatch() {
        startTime = System.currentTimeMillis();
        format = new FormatFromInterval();
    }

    public void start() {
        startTime = System.currentTimeMillis();
        isWork = true;
        new Thread(timeThread).start();
    }

    public void pause() {
        isWork = false;
        tempTime = getInterval();
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        isWork = false;
        tempTime = 0;
        startTime = System.currentTimeMillis();
        sendTime();
    }

    private Runnable timeThread = new Runnable() {
        @Override
        public void run() {
            while (isWork) {
                sendTime();
                if (onEventService != null) onEventService.updateTime(getViewTime());
                try {
                    Thread.sleep(PERIOD_UPDATE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private long getInterval() {
        return Math.abs(startTime - System.currentTimeMillis()) + tempTime;
    }

    private long getIntervalPause() {
        return Math.abs(tempTime);
    }

    private String getViewTime() {
        return format.getHour(getInterval()) + ":" + format.getMinute(getInterval()) + ":" + format.getSecond(getInterval());
    }

    private String getViewTimeMillisecond() {
        return format.getMillisecond(getInterval());
    }

    private String getViewTimePause() {
        return format.getHour(getIntervalPause()) + ":" + format.getMinute(getIntervalPause()) + ":" + format.getSecond(getIntervalPause());
    }

    private String getViewTimeMillisecondPause() {
        return format.getMillisecond(getIntervalPause());
    }

    private void sendTime() {
        if (handler != null) {
            Message msg = handler.obtainMessage(Recorder.MSG_UPDATE_TIME, 0, 0, new TimeRecorderEvent(getViewTime(), getViewTimeMillisecond()));
            handler.sendMessage(msg);
        }
    }

    public void sendTimePause() {
        if (handler != null) {
            Message msg = handler.obtainMessage(Recorder.MSG_UPDATE_TIME, 0, 0, new TimeRecorderEvent(getViewTimePause(), getViewTimeMillisecondPause()));
            handler.sendMessage(msg);
        }
    }


}
