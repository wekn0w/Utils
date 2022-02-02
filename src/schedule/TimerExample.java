package schedule;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimerExample {
    public void start() {
        new Timer(true).schedule(new TimerTask() {
            public void run() {
                new ActionExecutor().execute();
            }
        }, new Date(), TimeUnit.MINUTES.toMinutes(1));//1 per minute
    }
}