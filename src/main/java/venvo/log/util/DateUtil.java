package venvo.log.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author venvo
 * @date 2020-06-02 13:55
 * @description
 * @modified By
 * @version: jdk1.8
 */
public class DateUtil {


    public static Date string2Date(String time) {
        try {
            Date date = new Date();
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (time.length() <= 10) {
                time = time + " 00:00:00";
            }
            date = sdf.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param date 时间对象
     * @param hms  是否显示时分秒
     * @return
     */
    public static String date2String(Date date, Boolean hms) {
        DateFormat sdf;
        if (hms) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        String time = sdf.format(date);
        return time;
    }

    public static Timestamp string2Timestamp(String time) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        if (time.length() <= 10) {
            time = time + " 00:00:00";
        }
        ts = Timestamp.valueOf(time);
        return ts;
    }

    /**
     * @param ts  时间戳对象
     * @param hms 是否显示时分秒
     * @return
     */
    public static String timestamp2String(Timestamp ts, Boolean hms) {
        DateFormat sdf;
        if (hms) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        String tsStr = "";
        tsStr = sdf.format(ts);
        return tsStr;
    }

    public static Date timestamp2Date(Timestamp ts) {
        Date date = new Date();
        date = ts;
        return date;
    }

    public static Timestamp date2Timestamp(Date date) {
        String time = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = sdf.format(date);
        Timestamp ts = Timestamp.valueOf(time);
        return ts;
    }

    /**
     * 计算两个时间之间的小时差<br>
     * start - stop
     *
     * @param start 开始时间
     * @param stop  结束时间
     * @return
     */
    public static Long countHour(Date start, Date stop) {
        long diff = start.getTime() - stop.getTime();
        long hour = diff / (60 * 60 * 1000);
        return hour;
    }

    /**
     * 计算两个时间之间的分钟数差 <br>
     * start - stop
     *
     * @param start 开始时间
     * @param stop  结束时间
     * @return
     */
    public static Long countMinute(Date start, Date stop) {
        long diff = start.getTime() - stop.getTime();
        long min = diff / (60 * 1000);
        return min;
    }

    /**
     * 计算两个时间之间的秒数差<br>
     * start - stop
     *
     * @param start 开始时间
     * @param stop  结束时间
     * @return
     */
    public static Long countSecond(Date start, Date stop) {
        long diff = start.getTime() - stop.getTime();
        long sec = diff / 1000;
        return sec;
    }

    /**
     * 按天增加或减时间
     *
     * @param date
     * @param days  增减的天数
     * @param hms   是否显示时分秒
     * @param isAdd 加减标识，false 是减，true是加
     * @return
     */
    public static String addOrMinusDate(Date date, int days, Boolean hms, Boolean isAdd) {
        long d = (long) days;
        SimpleDateFormat df = null;
        if (hms) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            df = new SimpleDateFormat("yyyy-MM-dd");
        }
        if (!isAdd) {
            return df.format(new Date(date.getTime() - (d * 24 * 60 * 60 * 1000)));
        } else {
            return df.format(new Date(date.getTime() + (d * 24 * 60 * 60 * 1000)));
        }
    }

    /**
     * 判断两个日期是否同年同月
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return
     */
    public static boolean equals(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
    }
}
