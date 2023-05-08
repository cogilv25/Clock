package clock;

import java.io.File;
import java.util.Calendar;

/**
 * The Model part of the MVC architecture. Concerns itself with PriorityQueue
 * that stores alarms, and ICalFileHandler that handles file loading and saving.
 * 
 * @author Calum Lindsay
 */
public class Model{
    
    /**
     * Stores the current hour.
     */
    private int hour = 0;
    
    /**
     * Stores the current minute.
     */
    private int minute = 0;
    
    /**
     * Stores the current second.
     */
    private int second = 0;
    
    /**
     * Used to determine if the second has changed.
     */
    private int oldSecond = 0;
    
    /**
     * The alarm that is currently "ringing" otherwise null.
     */
    private Alarm activatedAlarm = null;
    
    /**
     * The hour that the next alarm in the queue will expire.
     */
    private int alarmHour = -1;
    
    /**
     * The minute that the next alarm in the queue will expire.
     */
    private int alarmMinute;
    
    /**
     * An instance of the class that loads and saves .ical files.
     */
    private ICalFileHandler iCalFile;
    
    /**
     * The queue that stores the alarms.
     */
    private PriorityQueue q;
    
    /**
     * A flag that is set true whenever a change is made to the queue.
     */
    private boolean qUpdated = false;
    
    /**
     * Creates a new Model.
     */
    public Model()
    {
        q = new PriorityQueue(4);
        iCalFile = new ICalFileHandler();
        update();
    }
    
    /**
     * Get whether the current time changed on the last call to update.
     * 
     * @return True if the time changed on the last call to update.
     */
    public boolean timeUpdated()
    {
        return oldSecond != second;
    }
    
    /**
     * Add a new Alarm with the alarmTime and message provided.
     * 
     * @param alarmTime The expiry time for the Alarm.
     * @param message The message to display when the Alarm expires.
     */
    public void addAlarm(Calendar alarmTime, String message)
    {
        q.add(alarmTime, message);
        try
        {
            //Update alarmHour & alarmMinute
            alarmHour = q.head().getAlarmTime().get(Calendar.HOUR);
            alarmMinute = q.head().getAlarmTime().get(Calendar.MINUTE);
        }
        //An exception here is theoretically impossible
        catch(Exception ignoreExceptions){}
        
        qUpdated = true;
    }
    
    /**
     * Sets the currently active File to the File provided. 
     * 
     * @param file The file to be set as the active file.
     */
    public void setActiveFile(File file)
    {
        iCalFile.setFile(file);
    }
    
    /**
     * Get the currently active File.
     * 
     * @return The currently active File or null if no File has been set.
     */
    public File getActiveFile()
    {
        return iCalFile.getFile();
    }
    
    /**
     * Save to the currently active File.
     * 
     * @return True if the operation succeeded.
     */
    public boolean saveStateToActiveFile()
    {
        if(iCalFile == null)
            return false;
        
        return iCalFile.saveAlarmQueue(q);
    }
    
    /**
     * Loads the currently active File.
     * 
     * @return True if the operation succeeded.
     */
    public boolean loadStateFromActiveFile()
    {
        if(iCalFile == null)
            return false;
        
        PriorityQueue temp;
        
        temp = iCalFile.loadAlarmQueue();
        
        if(temp != null)
        {
            q = temp;
            try
            {
                //Initialise alarmHour & alarmMinute
                alarmHour = q.head().getAlarmTime().get(Calendar.HOUR);
                alarmMinute = q.head().getAlarmTime().get(Calendar.MINUTE);
            }
            catch(Exception ignoreExceptions){}
            
            qUpdated = true;
            return true;
        }
        else
            return false;
    }
    
    /**
     * Remove the Alarm at the index provided from the queue.
     * 
     * @param index The index of the Alarm to remove.
     * @throws ArrayIndexOutOfBoundsException
     * @throws QueueUnderflowException 
     */
    public void removeAlarm(int index) throws ArrayIndexOutOfBoundsException, QueueUnderflowException
    {
        q.remove(index);
        qUpdated = true;
        //Update alarmHour & alarmMinute
        try
        {
        alarmHour = q.head().getAlarmTime().get(Calendar.HOUR);
        alarmMinute = q.head().getAlarmTime().get(Calendar.MINUTE);
        }
        catch (Exception e)
        {
            //Queue is empty
            alarmHour = -1;
        }
    }
    
    /**
     * Deactivate the activated Alarm.
     */
    public void stopActivatedAlarm()
    {
        activatedAlarm = null;
        
        try{
            //Update alarmHour & alarmMinute
            alarmHour = q.head().getAlarmTime().get(Calendar.HOUR);
            alarmMinute = q.head().getAlarmTime().get(Calendar.MINUTE);
        }
        catch(Exception e)
        {
            //If there was an exception then the queue is empty
            alarmHour = -1;
        }
    }
    
    /**
     * Get the hour of the next Alarm to expire.
     * 
     * @return The hour of the next alarm to expire. If the queue is empty
     * returns -1.
     */
    public int getAlarmHour()
    {
        return alarmHour;
    }
    
    /**
     * Get the minute of the next alarm to expire.
     * 
     * @return The minute of the next alarm to expire.
     */
    public int getAlarmMinute()
    {
        return alarmMinute;
    }
    
    /**
     * Get the current hour.
     * 
     * @return The current hour.
     */
    public int getCurrentHour()
    {
        return hour;
    }
    
    /**
     * Get the current minute.
     * 
     * @return The current minute.
     */
    public int getCurrentMinute()
    {
        return minute;
    }
    
    /**
     * Get the current second.
     * 
     * @return The current second.
     */
    public int getCurrentSecond()
    {
        return second;
    }
    
    /**
     * Get the currently activated alarm.
     * 
     * @return The currently activated Alarm or null.
     */
    public Alarm getActivatedAlarm()
    {
        return activatedAlarm;
    }
    
    /**
     * Get the PriorityQueue of Alarms.
     * 
     * @return The PriorityQueue containing all the Alarms.
     */
    public PriorityQueue getQueue()
    {
        return q;
    }
    
    /**
     * Get whether the queue has been updated since the flag was last reset.
     * You should reset the flag after performing any required UI updates.
     * 
     * @return A flag representing whether the queue has been updated.
     */
    public boolean getQueueUpdated()
    {
        return qUpdated;
    }
    
    /**
     * Reset the queue updated flag to false.
     */
    public void resetQueueUpdatedFlag()
    {
        qUpdated = false;
    }
    
    /**
     * Get whether or not the queue is empty.
     * 
     * @return True if the queue is empty.
     */
    public boolean isQueueEmpty()
    {
        return q.isEmpty();
    }
    
    /**
     * Get the alarm stored at the provided index within the queue.
     * 
     * @param index the index of the alarm required.
     * @return The alarm stored at the index within the queue.
     * @throws ArrayIndexOutOfBoundsException 
     */
    public Alarm getAlarm(int index) throws ArrayIndexOutOfBoundsException
    {
        return q.get(index);
    }
    
    /**
     * Returns the number of alarms stored in the queue.
     * 
     * @return The number of alarms stored in the queue.
     */
    public int getAlarmCount()
    {
        return q.getCount();
    }
    
    /**
     * Resets the internal queue.
     */
    public void resetQueue() 
    {
        q = new PriorityQueue(4);
        qUpdated = true;
        alarmHour = -1;
    }
    
    /**
     * Updates the time stored by the class and activates alarms that expire.
     */
    public void update()
    {
        Calendar date = Calendar.getInstance();
        try
        {
            if(!q.isEmpty())
                if(date.getTimeInMillis() > q.head().getAlarmTimeInMillis())
                {
                    activatedAlarm = q.head();
                    removeAlarm(0);
                }
        } 
        catch (Exception ignoreException){}
        
        hour = date.get(Calendar.HOUR);
        minute = date.get(Calendar.MINUTE);
        oldSecond = second;
        second = date.get(Calendar.SECOND);
        
    }
}