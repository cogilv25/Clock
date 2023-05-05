package clock;

import java.io.File;
import java.util.Calendar;
import java.util.Observable;

public class Model extends Observable {
    
    int hour = 0;
    int minute = 0;
    int second = 0;
    
    int oldSecond = 0;
    
    //The alarm that is currently activated or "ringing" otherwise null.
    Alarm activatedAlarm = null;
    int alarmHour = 0;
    int alarmMinute = 0;
    
    ICalFileHandler iCalFile;
    
    PriorityQueue q;
    
    boolean qUpdated = false;
    
    public Model()
    {
        q = new PriorityQueue(4);
        iCalFile = new ICalFileHandler();
        update();
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
        catch(Exception ignoreExceptions){}
    }
    
    public boolean isQueueEmpty()
    {
        return q.isEmpty();
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
        if (oldSecond != second)
        {
            setChanged();
            notifyObservers();
        }
    }
}