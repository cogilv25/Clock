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
    
    
    PriorityQueue q;
    
    public Model()
    {
        q = new PriorityQueue(4);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(c.getTimeInMillis()+10000);
        q.add(c, "Alarm 3");
        c = Calendar.getInstance();
        c.setTimeInMillis(c.getTimeInMillis()+2000);
        q.add(c, "Alarm 1");
        c = Calendar.getInstance();
        c.setTimeInMillis(c.getTimeInMillis()+5000);
        q.add(c, "Alarm 2");
        c = Calendar.getInstance();
        c.setTimeInMillis(c.getTimeInMillis()+18000);
        q.add(c, "Alarm 6");
        c = Calendar.getInstance();
        c.setTimeInMillis(c.getTimeInMillis()+14000);
        q.add(c, "Alarm 5");
        c = Calendar.getInstance();
        c.setTimeInMillis(c.getTimeInMillis()+12000);
        q.add(c, "Alarm 4");
        
        System.out.println(q.toString());
        update();
    }
    
    public void addAlarm()
    {
        
    }
    
    public void update()
    {
        Calendar date = Calendar.getInstance();
        try
        {
            if(!q.isEmpty())
                if(date.getTimeInMillis() > q.head().getAlarmTimeInMillis())
                {
                    System.out.println(q.head().getMessage());
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