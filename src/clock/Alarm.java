package clock;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Alarm
{
    private Calendar alarmTime;
    private String message;
    
    public Alarm(String message, Calendar alarmTime)
    {
        this.alarmTime = alarmTime;
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public Calendar getAlarmTime()
    {
        return alarmTime;
    }
    
    public long getAlarmTimeInMillis()
    {
        return alarmTime.getTimeInMillis();
    }
    
    public void setAlarmTime(Calendar alarmTime)
    {
        this.alarmTime = alarmTime;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    @Override
    public String toString() {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("HH:mm:ss - dd.MM.yy ");
        return "(" + simpleFormat.format(getAlarmTime().getTime()) + ", " + getMessage() + ")";
    }
}
