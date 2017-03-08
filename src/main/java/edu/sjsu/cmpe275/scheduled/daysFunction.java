package edu.sjsu.cmpe275.scheduled;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by fu on 12/17/16.
 */
public class daysFunction {
    public static int getDays(Timestamp end, Timestamp start){
        Calendar aCalendar = Calendar.getInstance();
        Calendar bCalendar = Calendar.getInstance();

        aCalendar.setTime(end);
        bCalendar.setTime(start);
        int days = 0;
        if(aCalendar.before(bCalendar))
        {
            while(aCalendar.before(bCalendar)){
                days++;
                aCalendar.add(Calendar.DAY_OF_YEAR, 1);
            }
        }else{
            while(bCalendar.before(aCalendar)){
                //System.out.println("day -- ");
                days--;
                bCalendar.add(Calendar.DAY_OF_YEAR, 1);
            }
        }


        return days;
    }

    public static Timestamp setDays(Timestamp start, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DATE, days);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(calendar.getTime());
        Timestamp returnTime = Timestamp.valueOf(time);
        return returnTime;
    }

}
