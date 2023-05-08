package clock;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Stores an Alarm.
 * 
 * @author Calum Lindsay
 */
public class Alarm
{
    /**
     * The expiry time of the Alarm.
     */
    private Calendar alarmTime;
    
    /**
     * The message to display to the user when the Alarm expires.
     */
    private String message;
    
    /**
     * Creates a new Alarm with the message and alarmTime provided.
     * 
     * @param message The message to display to the user when the Alarm expires.
     * @param alarmTime The expiry time of the Alarm.
     */
    public Alarm(String message, Calendar alarmTime)
    {
        this.alarmTime = alarmTime;
        this.message = message;
    }
    
    /**
     * Get the message to be displayed to the user when the Alarm expires.
     * 
     * @return The message to be displayed to the user when the Alarm expires.
     */
    public String getMessage()
    {
        return message;
    }
    
    /**
     * Get the expiry time of the Alarm.
     * 
     * @return The expiry time of the Alarm.
     */
    public Calendar getAlarmTime()
    {
        return alarmTime;
    }
    
    /**
     * Get the expiry time of the Alarm in milliseconds.
     * 
     * @return The expiry time of the Alarm in milliseconds.
     */
    public long getAlarmTimeInMillis()
    {
        return alarmTime.getTimeInMillis();
    }
    
    /**
     * Set the expiry time of the Alarm.
     * 
     * @param alarmTime The new expiry time of the Alarm.
     */
    public void setAlarmTime(Calendar alarmTime)
    {
        this.alarmTime = alarmTime;
    }
    
    /**
     * Set the message to be displayed to the user when the Alarm expires.
     * 
     * @param message The new message to be displayed to the user when the Alarm expires.
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    /**
     * Get a String representation of the Alarm.
     * 
     * @return a String representing the Alarm.
     */
    @Override
    public String toString() {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("HH:mm:ss - dd.MM.yy ");
        return "(" + simpleFormat.format(getAlarmTime().getTime()) + ", " + getMessage() + ")";
    }
}
