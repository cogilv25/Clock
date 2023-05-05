package clock;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class AlarmDialog
{
    private List<JComponent> components;
    
    private JTextField alarmMessage;
    private SpinnerModel hourSpinnerModel;
    private SpinnerModel minuteSpinnerModel;
    private SpinnerModel secondSpinnerModel;

    private String title;
    private int messageType;
    private JRootPane rootPane;
    private String[] options;
    private int optionIndex;

    public AlarmDialog()
    {
        components = new ArrayList<>();
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
        
        setTitle("Add or Edit an Alarm");
        setMessageType(JOptionPane.PLAIN_MESSAGE);
        setRootPane(null);
        setOptions(new String[] { "Confirm", "Cancel" });
        setOptionSelection(0);
        JPanel panel = new JPanel(new GridBagLayout());
        addComponent(panel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = .25;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel("Message:"),gbc);
        gbc.gridy = 1;
        panel.add(alarmMessage, gbc);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Hours"),gbc);
        gbc.gridx = 1;
        panel.add(new JLabel("Minutes"),gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Seconds"),gbc);
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(new JSpinner(hourSpinnerModel),gbc);
        gbc.gridx = 1;
        panel.add(new JSpinner(minuteSpinnerModel),gbc);
        gbc.gridx = 2;
        panel.add(new JSpinner(secondSpinnerModel),gbc);
        
        
    }
    
    public void setAlarm(Alarm alarm)
    {
        alarmMessage.setText(alarm.getMessage());
        hourSpinnerModel.setValue(alarm.getAlarmTime().get(Calendar.HOUR_OF_DAY));
        minuteSpinnerModel.setValue(alarm.getAlarmTime().get(Calendar.MINUTE));
        secondSpinnerModel.setValue(alarm.getAlarmTime().get(Calendar.SECOND));
    }
    
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

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setMessageType(int messageType)
    {
        this.messageType = messageType;
    }

    public void addComponent(JComponent component)
    {
        components.add(component);
    }

    public void addMessageText(String messageText)
    {
        JLabel label = new JLabel("<html>" + messageText + "</html>");

        components.add(label);
    }

    public void setRootPane(JRootPane rootPane)
    {
        this.rootPane = rootPane;
    }

    public void setOptions(String[] options)
    {
        this.options = options;
    }

    public void setOptionSelection(int optionIndex)
    {
        this.optionIndex = optionIndex;
    }

    public int show()
    {
        int optionType = JOptionPane.OK_CANCEL_OPTION;
        Object optionSelection = null;

        if(options.length != 0)
        {
            optionSelection = options[optionIndex];
        }

        int selection = JOptionPane.showOptionDialog(rootPane,
                components.toArray(), title, optionType, messageType, null,
                options, optionSelection);

        return selection;
    }

    public static String getLineBreak()
    {
        return "<br>";
    }
}
