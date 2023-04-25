package clock;

import java.io.File;
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
    
    
    void saveAlarmQueue(PriorityQueue queue)
    {
        if(file == null)
            return;
        
        if(file.exists())
        {
            
        }
        else
        {
            
        }
        //Write File
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
