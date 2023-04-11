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
    int alarmHour = 0;
    int alarmMinute = 0;
    
    PriorityQueue q;
    
    public Model()
    {
        q = new PriorityQueue(4);
        update();
        Calendar temp = Calendar.getInstance();
        temp.setTimeInMillis(temp.getTimeInMillis() + 3600000);
        addAlarm(temp, "Just a test");
    }
    
    public void addAlarm(Calendar alarmTime, String message)
    {
        q.add(alarmTime, message);
        try{
        //Update alarmHour & alarmMinute
        alarmHour = q.head().getAlarmTime().get(Calendar.HOUR);
        alarmMinute = q.head().getAlarmTime().get(Calendar.MINUTE);
        System.out.println("Hour = " + alarmHour + "\nMinute = " + alarmMinute);
        }
        catch(QueueUnderflowException e)
        {
            System.out.println("After adding an alarm there was no alarm in the queue");
        }
    }
    
    public void removeAlarm(int index) throws ArrayIndexOutOfBoundsException, QueueUnderflowException
    {
        q.remove(index);
        //Update alarmHour & alarmMinute
        alarmHour = q.head().getAlarmTime().get(Calendar.HOUR);
        alarmMinute = q.head().getAlarmTime().get(Calendar.MINUTE);
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
                    
                    //Update alarmHour & alarmMinute
                    alarmHour = q.head().getAlarmTime().get(Calendar.HOUR);
                    alarmMinute = q.head().getAlarmTime().get(Calendar.MINUTE);
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