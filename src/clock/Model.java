package clock;

import java.io.File;
import java.util.Calendar;

public class Model{
    
    int hour = 0;
    int minute = 0;
    int second = 0;
    
    int oldSecond = 0;
    
    //The alarm that is currently "ringing" otherwise null.
    Alarm activatedAlarm = null;
    int alarmHour = -1;
    int alarmMinute;
    
    ICalFileHandler iCalFile;
    
    PriorityQueue q;
    
    boolean qUpdated = false;
    
    public Model()
    {
        q = new PriorityQueue(4);
        iCalFile = new ICalFileHandler();
        update();
    }
    
    public boolean timeUpdated()
    {
        return oldSecond != second;
    }
    
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
    
    public void setActiveFile(File file)
    {
        iCalFile.setFile(file);
    }
    
    public File getActiveFile()
    {
        return iCalFile.getFile();
    }
    
    public boolean saveStateToActiveFile()
    {
        if(iCalFile == null)
            return false;
        
        return iCalFile.saveAlarmQueue(q);
    }
    
    public boolean loadStateFromActiveFile()
    {
        if(iCalFile == null)
            return false;
        
        PriorityQueue temp;
        try
        {
            temp = iCalFile.loadAlarmQueue();
        }
        catch(Exception e)
        {
            return false;
        }
        
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
    
    public void removeAlarm(int index) throws ArrayIndexOutOfBoundsException, QueueUnderflowException
    {
        q.remove(index);
        qUpdated = true;
        //Update alarmHour & alarmMinute
        alarmHour = q.head().getAlarmTime().get(Calendar.HOUR);
        alarmMinute = q.head().getAlarmTime().get(Calendar.MINUTE);
    }
    
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
        }
    }
    
    public int getAlarmHour()
    {
        return alarmHour;
    }
    
    public int getAlarmMinute()
    {
        return alarmMinute;
    }
    
    public boolean isQueueEmpty()
    {
        return q.isEmpty();
    }
    
    public int getQueueCount()
    {
        return q.getCount();
    }
    
    public Alarm getAlarm(int index) throws ArrayIndexOutOfBoundsException
    {
        return q.get(index);
    }
    
    public int getNumberOfAlarms()
    {
        return q.getCount();
    }
    
    void resetQueue() 
    {
        q = new PriorityQueue(4);
        qUpdated = true;
        alarmHour = -1;
    }
    
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
        /*if (oldSecond != second)
        {
            setChanged();
            notifyObservers();
        }*/
    }
}