package edu.sjsu.cmpe275.scheduled;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * Created by fu on 12/16/16.
 */
@Component
public interface Task {
    public void expireTask();
}
