package clock;

import java.awt.event.*;
import javax.swing.Timer;

/* Essentially the MVC architecture boils down to Controller (this class)
 * receives input through the actionPerformed class, then it calls functions in
 * Model and passes the return values to View for example:
 * 
 * view.showThing(model.doThing());                                           */

public class Controller implements ActionListener {
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
                    alarm.setAlarmTime(newAlarm.getAlarmTime());
                    alarm.setMessage(newAlarm.getMessage());
                    model.qUpdated = true;
                }
                break;
            case "Remove":
                try {model.removeAlarm(view.getAlarmEditorSelectionIndex());}
                catch(QueueUnderflowException throwaway){}
                break;

        }
    }
}