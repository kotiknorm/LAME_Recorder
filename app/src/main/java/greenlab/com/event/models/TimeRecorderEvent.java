package greenlab.com.event.models;

/**
 * Created by Metrolog on 25.10.14.
 */
public class TimeRecorderEvent {

    public String time;

    public String millisecond;

    public TimeRecorderEvent(String _time, String _millisecond){
        time = _time;
        millisecond = _millisecond;
    }

}
