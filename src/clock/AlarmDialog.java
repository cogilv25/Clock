package clock;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Calendar;
import javax.swing.*;

/**
 * Constructs and displays the Add/Edit Alarm dialog.
 * 
 * @author Calum Lindsay
 */
public class AlarmDialog
{
    /**
     * The Panel upon which the other components are placed.
     */
    private final JPanel panel;
    
    /**
     * The text box that holds the message of the Alarm being created.
     */
    private final JTextField alarmMessage;
    
    /**
     * The JSpinner that holds the expiry hour of the Alarm being created.
     */
    private final SpinnerModel hourSpinnerModel;
    
    /**
     * The JSpinner that holds the expiry minute of the Alarm being created.
     */
    private final SpinnerModel minuteSpinnerModel;
    
    /**
     * The JSpinner that holds the expiry second of the Alarm being created.
     */
    private final SpinnerModel secondSpinnerModel;
    
    /**
     * 
     */
    private final String[] options;

    /**
     * Create a new AlarmDialog instance.
     */
    public AlarmDialog()
    {
        //Initialize controls.
        panel = new JPanel(new GridBagLayout());
        alarmMessage = new JTextField();

        Calendar now = Calendar.getInstance();
        
        hourSpinnerModel = new SpinnerNumberModel(
            (now.get(Calendar.HOUR_OF_DAY) + 1) % 24,
            0, 23, 1);
        
        minuteSpinnerModel = new SpinnerNumberModel(
            now.get(Calendar.MINUTE),
            0, 59, 1);
        
        secondSpinnerModel = new SpinnerNumberModel(
            now.get(Calendar.SECOND),
            0, 59, 1);
        
        options = new String[] { "Confirm", "Cancel" };
        
        //Layout code starts here
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = .25;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        //Message label and textbox.
        panel.add(new JLabel("Message:"),gbc);
        gbc.gridy = 1;
        panel.add(alarmMessage, gbc);
        
        //Labels for JSpinners.
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Hours"),gbc);
        gbc.gridx = 1;
        panel.add(new JLabel("Minutes"),gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Seconds"),gbc);
        
        //JSpinners.
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(new JSpinner(hourSpinnerModel),gbc);
        gbc.gridx = 1;
        panel.add(new JSpinner(minuteSpinnerModel),gbc);
        gbc.gridx = 2;
        panel.add(new JSpinner(secondSpinnerModel),gbc);
        
    }
    
    
    /**
     * Set the Alarm stored by the dialog to the one provided.
     * 
     * @param alarm The Alarm to be stored in the dialog.
     */
    public void setAlarm(Alarm alarm)
    {
        alarmMessage.setText(alarm.getMessage());
        hourSpinnerModel.setValue(alarm.getAlarmTime().get(Calendar.HOUR_OF_DAY));
        minuteSpinnerModel.setValue(alarm.getAlarmTime().get(Calendar.MINUTE));
        secondSpinnerModel.setValue(alarm.getAlarmTime().get(Calendar.SECOND));
    }
    
    /**
     * Get the Alarm stored by the dialog.
     * 
     * @return The Alarm stored by the dialog.
     */
    public Alarm getAlarm()
    {
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.HOUR_OF_DAY, (Integer)hourSpinnerModel.getValue());
        alarmTime.set(Calendar.MINUTE, (Integer)minuteSpinnerModel.getValue());
        alarmTime.set(Calendar.SECOND, (Integer)secondSpinnerModel.getValue());
        
        //If the time provided is before the current time increase the time
        // provided by 24h making the alarm go off tomorrow.
        if(alarmTime.getTimeInMillis() < Calendar.getInstance().getTimeInMillis())
            alarmTime.setTimeInMillis(alarmTime.getTimeInMillis()+(1000*60*60*24));
        
        return new Alarm(alarmMessage.getText(), alarmTime);
    }

    /**
     * Shows the dialog to the user.
     * 
     * @return True unless the user hits cancel.
     */
    public boolean show()
    {
        int optionType = JOptionPane.OK_CANCEL_OPTION;

        int selection = JOptionPane.showOptionDialog(null,
            panel, "Add or Edit an Alarm", optionType,
            JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        return selection == JOptionPane.OK_OPTION;
    }
}
