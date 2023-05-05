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
        timer = new Timer(100, this);
        timer.setActionCommand("clockTimer");
        timer.start();
        
        //Ask the user if they want to open a file and open it.
        if(!view.promptYesNo("Would you like to open a file?"))
            return;
        
        File file = view.showOpenFileDialog();
        if(file == null)
            return;
        
        model.setActiveFile(file);
        if(!model.loadStateFromActiveFile())
            view.displayPopupBox("Failed to open the file provided! Please try "
                    + "again or select another file");
    }

    @Override
    public void windowClosing(WindowEvent e)
    { 
        //If there is nothing to save then just exit
        if(model.isQueueEmpty())
            System.exit(0);
        
        boolean shouldSave = view.promptYesNo("Would you like to save before exiting?");
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
        String command = e.getActionCommand();
        
        //Speculative optimization to exit early on hot path.
        if(command.equals("clockTimer"))
        {
            model.update();
            return;
        }
        
        System.out.println(e.getActionCommand());
        Alarm alarm;
        File file;
        switch(e.getActionCommand())
        {
            case "Show Alarm Editor...":
                view.showAlarmEditorItem.setText("Hide Alarm Editor...");
                view.alarmEditorMenuItem.setState(true);
                view.toggleAlarmEditorVisibility();
                break;
            case "Hide Alarm Editor...":
                view.showAlarmEditorItem.setText("Show Alarm Editor...");
                view.alarmEditorMenuItem.setState(false);
                view.toggleAlarmEditorVisibility();
                break;
            case "Alarm Editor":
                String value = view.alarmEditorMenuItem.getState() ? 
                        "Hide Alarm Editor..." : "Show Alarm Editor...";
                view.showAlarmEditorItem.setText(value);
                view.toggleAlarmEditorVisibility();
                break;
            case "24h Clock":
                break;
            case "Digital Clock":
                break;
            case "Reset All Alarms":
                model.resetQueue();
                break;
            case "Add":
            case "Add Alarm":
                alarm = view.showAlarmDialog();
                if(alarm != null)
                    model.addAlarm(alarm.getAlarmTime(), alarm.getMessage());
                break;
            case "Edit":
                alarm = model.getAlarm(view.getAlarmEditorSelectionIndex());
                Alarm newAlarm = view.showAlarmDialog(alarm);
                if(newAlarm != null)
                {
                    try{model.removeAlarm(view.getAlarmEditorSelectionIndex());}
                    catch(Exception throwaway){}
                    
                    model.addAlarm(newAlarm.getAlarmTime(), newAlarm.getMessage());
                }
                break;
            case "New":
                
            case "Remove":
                int indexToRemove = view.getAlarmEditorSelectionIndex();
                
                System.out.println("List Index To Remove: " + indexToRemove);
                if(indexToRemove < 0)
                    break;
                
                try {model.removeAlarm(indexToRemove);}
                catch(QueueUnderflowException throwaway){}
                break;
            case "Open":
                if(!model.setActiveFile(view.showOpenFileDialog()))
                    System.out.println("Failed to load file!");
                else if(!model.loadStateFromActiveFile())
                    System.out.println("Failed to load file!");
                break;
            case "Save as...":
                if(!model.setActiveFile(view.showSaveFileDialog()))
                    System.out.println("Failed to save file!");
            case "Save":
                if(!model.saveStateToActiveFile())
                    System.out.println("Failed to save file!");
                break;

        }
    }
}