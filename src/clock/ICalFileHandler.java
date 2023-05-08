package clock;

import java.io.*;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Loads an iCalendar file from file into a PriorityQueue or saves a 
 * PriorityQueue to a file using the iCalendar format. Only implements a subset
 * of the iCalendar format and thus only guarantees to read files that it has
 * written.
 * 
 * @author Calum Lindsay
 */
class ICalFileHandler {
    
    /**
     * The active activeFile that will be saved to or loaded from.
     */
    private File activeFile;
    
    
    /**
     * Saves the PriorityQueue provided to the active file in the iCalendar 
     * format.
     * 
     * @param queue The PriorityQueue to save.
     * @return True if the save operation completed successfully.
     */
    boolean saveAlarmQueue(PriorityQueue queue)
    {
        if(activeFile == null)
            return false;
        try
        {
            FileWriter writer = new FileWriter(activeFile.toString());
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
    
    /**
     * Loads a PriorityQueue from the active file.
     * 
     * @return The PriorityQueue loaded or null if the operation failed.
     */
    PriorityQueue loadAlarmQueue()
    {
        if(activeFile == null)
            return null;
        
        if(!activeFile.exists())
            return null;
        
        List<String> buffer;
        
        try{
            buffer = Files.readAllLines(activeFile.toPath());
        }
        catch(IOException e)
        {
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
                        return null;
                    }
                    if(cal.getTimeInMillis() > Calendar.getInstance().getTimeInMillis())
                        q.add(cal, message);
                    
                    message = null;
                    DTStamp = null;
                    inEvent = false;
                }
                    
        }
        return q;
    }
    
    /**
     * Get the current active file.
     * 
     * @return The current active file.
     */
    File getFile()
    {
        return activeFile;
    }
    
    /**
     * Sets the active file.
     * 
     * @param file The new active file.
     */
    void setFile(File file)
    {
        this.activeFile = file;
    }
}
