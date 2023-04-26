package clock;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Calum
 */
class ICalFileHandler {
    private File file;
    
    
    boolean saveAlarmQueue(PriorityQueue queue)
    {
        if(file == null)
            return false;
        try
        {
            FileWriter writer = new FileWriter(file.toString());
            writer.write("BEGIN:VCALENDAR\r\n");
            writer.write("VERSION:2.0\r\n");
            writer.write("PRODID:-//cll-ass2//NONSGML clock v1.0//EN");
            for(int i = 0; i < queue.getCount(); i++)
            {
                Calendar time = queue.get(i).getAlarmTime();
                
                String year = Integer.toString(time.get(Calendar.YEAR));
                String month = String.format("%02d", time.get(Calendar.MONTH) + 1);
                String day = String.format("%02d", time.get(Calendar.DAY_OF_MONTH));
                
                String hour = String.format("%02d", time.get(Calendar.HOUR_OF_DAY));
                String min = String.format("%02d", time.get(Calendar.MINUTE));
                String sec = String.format("%02d", time.get(Calendar.SECOND));
                
                String DTStamp = year + month + day + 'T' + hour + min + sec;
                
                writer.write("\r\nBEGIN:VEVENT\r\n");
                writer.write("DTSTAMP:" + DTStamp + "\r\n");
                writer.write("SUMMARY:" + queue.get(i).getMessage() + "\r\n");
                writer.write("METHOD:ALARM\r\n");
                writer.write("UID:" + DTStamp + "-clock@cll-" + i + "\r\n");
                writer.write("DTSTART:" + DTStamp + "\r\n");
                writer.write("TRANSP:TRANSPARENT\r\n");
                writer.write("END:VEVENT");
            }
            writer.close();
        }
        catch(IOException e)
        {
            return false;
        }
        return true;
    }
    
    PriorityQueue loadAlarmQueue()
    {
        if(file == null)
            throw new IllegalArgumentException();
        
        if(!file.exists())
            throw new IllegalArgumentException();
        
        List<String> buffer;
        
        try{
            buffer = Files.readAllLines(file.toPath());
        }
        catch(IOException e)
        {
            System.out.println("IOEXCEPTION");
            return null;
        }
        
        PriorityQueue q = new PriorityQueue(4);
        boolean inEvent = false;
        String message = null;
        String DTStamp = null;
        
        for (String line : buffer)
        {
            if(line.startsWith("BEGIN:VEVENT"))
                if(inEvent)
                    return null;
                else
                    inEvent = true;
            else if(line.startsWith("DTSTAMP:"))
                if(!inEvent)
                    return null;
                else
                    DTStamp = line.substring(line.indexOf(':') + 1);
            else if(line.startsWith("SUMMARY:"))
                if(!inEvent)
                    return null;
                else
                    message = line.substring(line.indexOf(':') + 1);
            else if(line.startsWith("END:VEVENT"))
                if(!inEvent)
                    return null;
                else if(message == null || DTStamp == null )
                    return null;
                else
                {
                    Calendar cal = Calendar.getInstance();
                    try 
                    {
                        cal.setTime((new SimpleDateFormat("yyyyMMdd'T'HHmmss").parse(DTStamp)));
                    } 
                    catch (ParseException e) 
                    {
                        System.out.println(e.getMessage());
                        return null;
                    }
                    q.add(cal, message);
                    message = null;
                    DTStamp = null;
                    inEvent = false;
                }
                    
        }
        return q;
    }
    
    void setFile(File file)
    {
        this.file = file;
    }
}
