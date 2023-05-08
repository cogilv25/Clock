package clock;

import java.awt.event.*;
import java.io.File;
import javax.swing.Timer;

/* Essentially the MVC architecture boils down to Controller (this class)
 * receives input through the actionPerformed class, then it calls functions in
 * Model and passes the return values to View for example:
 * 
 * view.showThing(model.doThing());                                           */

public class Controller extends WindowAdapter implements ActionListener {
    Timer timer;
    
    Model model;
    View view;
    
    public Controller(Model m, View v) {
        model = m;
        view = v;
    }
    
    public void begin()
    {
        view.initializeCallbacks(this);
        
        timer = new Timer(100, this);
        timer.setActionCommand("clockTimer");
        timer.start();
        
        //Ask the user if they want to open a file and open it.
        if(!view.showYesNoDialog("Would you like to open a file?"))
            return;
        
        File file = view.showOpenFileDialog();
        if(file == null)
            return;
        
        model.setActiveFile(file);
        if(!model.loadStateFromActiveFile())
            view.displayPopupBox("Failed to open the file provided! Please try "
                    + "again or select another file");
        
        view.updateAlarmHand(model.alarmHour, model.alarmMinute);
    }

    @Override
    public void windowClosing(WindowEvent e)
    { 
        //If there is nothing to save then just exit
        if(model.isQueueEmpty())
            System.exit(0);
        
        boolean shouldSave = view.showYesNoDialog("Would you like to save before exiting?");
        if(!shouldSave)
            System.exit(0);
        
        File file = model.getActiveFile();
        if(file == null)
        {
            file = view.showSaveFileDialog();
            //If the user clicks cancel we just exit
            if(file == null)
                System.exit(0);

            model.setActiveFile(file);
        }
        //Now that we have a file the user wishes to save we loop until we
        // manage to save a file or the user cancels the operation. This is
        // due to the fact that fileio fails fairly regularly.

        while(file!=null)
        {
            if(!model.saveStateToActiveFile())
            {
                view.displayPopupBox("Failed to save the file, please choose another filename");
                file = view.showSaveFileDialog();
            }
            else
            {
                break;
            }
        }
        
        System.exit(0);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        
        Alarm alarm;
        File file;
        switch(e.getActionCommand())
        {
            case "clockTimer":
                model.update();
                if(model.timeUpdated())
                {
                    view.updateTime(model.hour, model.minute, model.second);
                }
                if(model.qUpdated)
                {
                    view.updateAlarmEditor(model.q);
                }
                if(model.activatedAlarm != null)
                {
                    view.displayPopupBox(model.activatedAlarm.getMessage());
                    model.stopActivatedAlarm();
                    view.updateAlarmHand(model.alarmHour, model.alarmMinute);
                }
                break;
                
            case "Show Alarm Editor...":
            case "Hide Alarm Editor...":
            case "Alarm Editor":
                view.toggleAlarmEditorVisibility();
                break;
                
            case "New":
                model.setActiveFile(null);
            case "Reset All Alarms":
                model.resetQueue();
                view.updateAlarmHand(model.alarmHour, model.alarmMinute);
                break;
                
            case "Add":
            case "Add Alarm":
                alarm = view.showAlarmDialog();
                if(alarm != null)
                {
                    model.addAlarm(alarm.getAlarmTime(), alarm.getMessage());
                    view.updateAlarmHand(model.alarmHour, model.alarmMinute);
                }
                break;
                
            case "Edit":
                alarm = model.getAlarm(view.getAlarmEditorSelectionIndex());
                Alarm newAlarm = view.showAlarmDialog(alarm);
                if(newAlarm != null)
                {
                    try{model.removeAlarm(view.getAlarmEditorSelectionIndex());}
                    catch(Exception throwaway){}
                    
                    model.addAlarm(newAlarm.getAlarmTime(), newAlarm.getMessage());
                    view.updateAlarmEditor(model.q);
                    view.updateAlarmHand(model.alarmHour, model.alarmMinute);
                }
                break;
                
            case "Remove":
                int indexToRemove = view.getAlarmEditorSelectionIndex();
                if(indexToRemove < 0)
                    break;
                
                try { model.removeAlarm(indexToRemove); }
                catch(QueueUnderflowException throwaway){ break; }
                
                view.updateAlarmHand(model.alarmHour, model.alarmMinute);
                break;
                
            case "Open":
                file = view.showOpenFileDialog();
                
                if(file ==  null)
                    break;
                
                model.setActiveFile(file);
                if(!model.loadStateFromActiveFile())
                    view.displayPopupBox("Failed to load file!");
                else
                    view.updateAlarmHand(model.alarmHour, model.alarmMinute);
                break;
                
            case "Save as...":
                file = view.showSaveFileDialog();
                
                if(file ==  null)
                    break;
                model.setActiveFile(file);
                
                if(!model.saveStateToActiveFile())
                    view.displayPopupBox("Failed to save file!");
                break; 
                
            case "Save":
                if(model.getActiveFile() == null)
                {
                    file = view.showSaveFileDialog();
                    
                    if(file ==  null)
                        break;
                    model.setActiveFile(file);
                }
                    
                if(!model.saveStateToActiveFile())
                    view.displayPopupBox("Failed to save file!");
                break;
                
            case "Application":
                view.displayPopupBox(
                "A simple application that displays a clock,"
                        + " stores a list of alarms,indicates\n"
                        
                + "when an alarm 'expires' and, allows saving,"
                        + " loading and manipulation of\n"
                        
                + "said list of alarms.\n\nVersion : 1.0.0");
                break;
                
            case "Author":
                view.displayPopupBox(
                "Name: Calum Lindsay\n"
                + "Github: github.com/cogilv25\n"
                + "Contact: 21010093@uhi.ac.uk");
                break;
                
            case "24h Clock":
                break;
            case "Digital Clock":
                view.toggleDigitalAnalogue();
                break;
            case "Set Alarm Sound":
                break;

        }
    }
}