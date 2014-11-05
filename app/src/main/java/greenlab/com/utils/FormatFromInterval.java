package greenlab.com.utils;

/**
 * Created by Metrolog on 11.10.14.
 */

public class FormatFromInterval {

    public String getSecond(long interval) {
        long a = Math.abs(interval) / 1000 % 60;
        if (a < 10) return "0" + a;
        return a + "";
    }

    public String getMinute(long interval) {
        long a = Math.abs(interval) / 1000 / 60 % 60;
        if (a < 10) return "0" + a;
        return a + "";
    }

    public String getHour(long interval) {
        long a = Math.abs(interval) / 1000 / 3600 % 60;
        if (a < 10) return "0" + a;
        return a + "";
    }

    public String getMillisecond(long interval) {
        long a = Math.abs(interval) % 1000;
        if (a < 10) return "00" + a;
        if (a < 100) return "0" + a;
        return "" + a;
    }

}
