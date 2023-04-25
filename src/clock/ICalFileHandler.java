package clock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
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
        File temp = null;
        if(file == null)
            return;
        
        if(file.exists())
        {
            temp = Paths.get(file.getAbsolutePath() + ".temp").toFile();
            try {
                Files.move(file.toPath(),temp.toPath(),REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(ICalFileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Write File
    }
    
    PriorityQueue loadAlarmQueue()
    {
        if(file == null)
            throw new IllegalArgumentException();
        
        if(!file.exists())
            throw new IllegalArgumentException();
        
        return new PriorityQueue(4);
    }
    
    void setFile(File file)
    {
        this.file = file;
    }
}
