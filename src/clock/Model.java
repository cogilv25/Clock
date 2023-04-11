package clock;

import java.util.Calendar;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.swing.JOptionPane;
//import java.util.GregorianCalendar;

public class Model extends Observable {
    
    int hour = 0;
    int minute = 0;
    int second = 0;
    
    int oldSecond = 0;
    
    //The alarm that is currently activated or "ringing" otherwise null.
    Alarm activatedAlarm = null;
    
    PriorityQueue q;
    
    public Model()
    {
        q = new PriorityQueue(4);
        update();
    }
    
    public void addAlarm(Calendar alarmTime, String message)
    {
        q.add(alarmTime, message);
    }
    
    public void removeAlarm(int index) throws ArrayIndexOutOfBoundsException, QueueUnderflowException
    {
        q.remove(index);
    }
    
    public void stopActivatedAlarm()
    {
        activatedAlarm = null;
    }
    
    public Alarm getAlarm(int index) throws ArrayIndexOutOfBoundsException
    {
        return q.get(index);
    }
    
    public int getNumberOfAlarms()
    {
        return q.getCount();
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
                    q.remove(0);
                }
        } 
        catch (QueueUnderflowException | ArrayIndexOutOfBoundsException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
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