package clock;

import java.awt.*;
import java.io.File;
import java.util.Calendar;
import javax.swing.*;


/**
 * The View part of the MVC architecture. Performs all actions to do with the
 * user interface.
 * 
 * @author Calum Lindsay
 */
public class View{
    
    /**
     * The JFrame that holds the main windows components.
     */
    private JFrame frame;
    
    /**
     * The ClockPanel instance that draws the clock.
     */
    private ClockPanel panel;
    
    /**
     * The AlarmDialog instance that allows the user to add or edit Alarms.
     */
    private AlarmDialog alarmDialog;
    
    /**
     * The ListModel that stores the Alarms being displayed in the alarm editor.
     */
    private DefaultListModel listModel;
    
    /**
     * Flag that indicates if the alarm editor is visible.
     */
    private boolean alarmEditorVisible = false;
    
    /**
     * The minimum width of the window when the alarm editor is visible.
     */
    private int minWidth;
    
    /**
     * The minimum width of the window when the alarm editor is not visible.
     */
    private int minWidthNoAlarmEditor;
    
    /**
     * The minimum height of the window.
     */
    private int minHeight;
    
    /**
     * Unimplemented.
     */
    private boolean digitalClockDisplayed = false;
    
    /**
     * Unimplemented.
     */
    private boolean twentyFourHourMode = false;
    
    /* --------------------- Menu bar menus and items ----------------------- */
    /* ------------ Indentation is used to indicate submenu items ----------- */
    
    /**
     * The file menu on the menu bar.
     */
    private JMenu fileMenu;
        /**
         * The new menu item within the file menu.
         */
        private JMenuItem newMenuItem;
        
        /**
         * The open menu item within the file menu.
         */
        private JMenuItem openMenuItem;
        
        /**
         * The save menu item within the file menu.
         */
        private JMenuItem saveMenuItem;
        
        /**
         * The save as menu item within the file menu.
         */
        private JMenuItem saveAsMenuItem;

    /**
     * The edit menu on the menu bar.
     */
    private JMenu editMenu;
        /**
         * The add alarm menu item within the edit menu.
         */
        private JMenuItem addAlarmMenuItem;
        
        /**
         * The show alarm editor menu item within the edit menu. The text of the
         * menu item toggles to hide alarm editor when the alarm editor is visible.
         */
        private JMenuItem showAlarmEditorItem;
        
        /**
         * The reset alarms menu item within the edit menu.
         */
        private JMenuItem resetAlarmsMenuItem;
        
        /**
         * The set alarm sound menu item within the edit menu. Unimplemented.
         */
        private JMenuItem alarmSoundMenuItem;

    /**
     * The view menu on the menu bar.
     */
    private JMenu viewMenu;
        /**
         * The 24h clock toggle-able menu item within the view menu.
         * Unimplemented.
         */
        private JCheckBoxMenuItem clock24hMenuItem;
        
        /**
         * The digital clock toggle-able menu item within the view menu.
         * Unimplemented.
         */
        private JCheckBoxMenuItem digitalClockMenuItem;
        
        /**
         * The alarm editor toggle-able menu item within the view menu.
         */
        private JCheckBoxMenuItem alarmEditorMenuItem;

    /**
     * The about menu on the menu bar.
     */
    private JMenu aboutMenu;
        /**
         * The application menu item within the about menu.
         */
        private JMenuItem aboutAppMenuItem;
        
        /**
         * The author menu item within the about menu.
         */
        private JMenuItem aboutAuthorMenuItem;
    
    /**
     * The alarm editor panel.
     */
    private JPanel alarmEditorPanel;
        /**
         * The scroll pane to make the contained list scroll-able.
         */
        private JScrollPane alarmEditorPane;
            /**
             * The list within the alarm editor that displays the queue of alarms.
             */
            private JList alarmEditorList;
            
        /**
         * The add button within the alarm editor.
         */
        private JButton addButton;
        
        /**
         * The edit button within the alarm editor.
         */
        private JButton editButton;
        
        /**
         * The remove button within the alarm editor.
         */
        private JButton removeButton;
    
    
    /**
     * Create a new View.
     */
    public View()
    {
        
        alarmDialog = new AlarmDialog();
        
        frame = new JFrame();
        frame.setLayout(new GridLayout(1,1));
        panel = new ClockPanel();
        
        frame.setTitle("Clock");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        
        /* -------------------- Construct Menu Bar -------------------------- */
        JMenuBar menuBar = new JMenuBar();
        
        /* ------------------- Construct File Menu -------------------------- */
        fileMenu = new JMenu("File");
        newMenuItem = new JMenuItem("New");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");
        saveAsMenuItem = new JMenuItem("Save as...");
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        menuBar.add(fileMenu);
        
        /* ------------------- Construct Edit Menu -------------------------- */
        editMenu = new JMenu("Edit");
        addAlarmMenuItem = new JMenuItem("Add Alarm");
        showAlarmEditorItem = new JMenuItem("Show Alarm Editor...");
        resetAlarmsMenuItem = new JMenuItem("Reset All Alarms");
        alarmSoundMenuItem = new JMenuItem("Set Alarm Sound");
        
        editMenu.add(addAlarmMenuItem);
        editMenu.add(showAlarmEditorItem);
        editMenu.add(resetAlarmsMenuItem);
        editMenu.add(alarmSoundMenuItem);
        menuBar.add(editMenu);
        
        /* ------------------- Construct View Menu -------------------------- */
        viewMenu = new JMenu("View");
        clock24hMenuItem = new JCheckBoxMenuItem("24h Clock");
        clock24hMenuItem.setEnabled(digitalClockDisplayed);
        digitalClockMenuItem = new JCheckBoxMenuItem("Digital Clock");
        alarmEditorMenuItem = new JCheckBoxMenuItem("Alarm Editor");
        viewMenu.add(clock24hMenuItem);
        viewMenu.add(digitalClockMenuItem);
        viewMenu.add(alarmEditorMenuItem);
        menuBar.add(viewMenu);
        
        /* ------------------- Construct About Menu ------------------------- */
        
        
        aboutMenu = new JMenu("About");
        aboutAppMenuItem = new JMenuItem("Application");
        aboutAuthorMenuItem = new JMenuItem("Author");
        aboutMenu.add(aboutAppMenuItem);
        aboutMenu.add(aboutAuthorMenuItem);
        menuBar.add(aboutMenu);
        
        frame.setJMenuBar(menuBar);
        
        frame.getContentPane().add(panel);
        
        /* ----------------- Construct Alarm Editor ------------------------- */
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        removeButton = new JButton("Remove");
        listModel = new DefaultListModel();
        alarmEditorList = new JList(listModel);
        alarmEditorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        alarmEditorPane = new JScrollPane(alarmEditorList);
        
        alarmEditorPanel = new JPanel();
        GroupLayout alarmEditorLayout = new GroupLayout(alarmEditorPanel);
        alarmEditorPanel.setLayout(alarmEditorLayout);
                
        alarmEditorLayout.setAutoCreateGaps(true);
        alarmEditorLayout.setAutoCreateContainerGaps(true);
        
        /* The layout for the alarm editor proved to be a major pain to figure
         * out, however once you get a handle on it the GroupLayout Manager is 
         * actually pretty nice to use if not so easy to look at. Here are 3
         * good resources:
         *
         * https://docs.oracle.com/javase/tutorial/uiswing/layout/group.html
         * https://docs.oracle.com/javase/8/docs/api/javax/swing/GroupLayout.Alignment.html
         * https://stackoverflow.com/questions/30863960/adding-a-scroll-pane-to-gridbaglayout */
        
        alarmEditorLayout.setHorizontalGroup(alarmEditorLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    
                .addComponent(alarmEditorPane)
                
                .addGroup(alarmEditorLayout.createSequentialGroup()
                    .addComponent(addButton)
                    .addComponent(editButton)
                    .addComponent(removeButton)
                )
        );
        
        alarmEditorLayout.setVerticalGroup(alarmEditorLayout.createSequentialGroup()
                    
                .addComponent(alarmEditorPane)
                    
                .addGroup(alarmEditorLayout.createParallelGroup()
                    .addComponent(addButton)
                    .addComponent(editButton)
                    .addComponent(removeButton)
                )
        );
        alarmEditorPanel.add(alarmEditorPane);
        
        /* ----------------- Calculate Minimum Sizes ------------------------ */
        
        Insets insets = frame.getInsets();
        
        // The magic numbers are to create slightly more padding where desired.
        
        minWidthNoAlarmEditor = (addButton.getMinimumSize().width + 
            editButton.getMinimumSize().width + removeButton.getMinimumSize().width + 40);
        minWidth = minWidthNoAlarmEditor * 2;
        
        minHeight = minWidthNoAlarmEditor + insets.top + insets.bottom + 55;
        
        minWidth += insets.left + insets.right;
        minWidthNoAlarmEditor += insets.left + insets.right;
        
        /* ----------------- Centre Window on Screen ------------------------ */
         
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        frame.setBounds(screenSize.width / 2 - 250, screenSize.height / 2 - 250,
                500, 500);
        frame.setMinimumSize(new Dimension(minWidthNoAlarmEditor, minHeight));
        frame.setPreferredSize(new Dimension(500, 500));
        
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Initializes all the controls in the view to send their events to the
     * provided controller.
     * 
     * @param controller The controller to send events to. 
     */
    public void initializeCallbacks(Controller controller)
    {
        frame.addWindowListener(controller);
        
        newMenuItem.addActionListener(controller);
        openMenuItem.addActionListener(controller);
        saveMenuItem.addActionListener(controller);
        saveAsMenuItem.addActionListener(controller);
        
        addAlarmMenuItem.addActionListener(controller);
        showAlarmEditorItem.addActionListener(controller);
        resetAlarmsMenuItem.addActionListener(controller);
        alarmSoundMenuItem.addActionListener(controller);
        
        clock24hMenuItem.addActionListener(controller);
        digitalClockMenuItem.addActionListener(controller);
        alarmEditorMenuItem.addActionListener(controller);
        
        aboutAppMenuItem.addActionListener(controller);
        aboutAuthorMenuItem.addActionListener(controller);
        
        addButton.addActionListener(controller);
        editButton.addActionListener(controller);
        removeButton.addActionListener(controller);
    }
    
    /**
     * Toggles the visibility of the alarm editor. Expands the size of the
     * window if it is below the minimum size when showing the alarm editor.
     */
    public void toggleAlarmEditorVisibility()
    {
        
        Container pane = frame.getContentPane();
        if(alarmEditorVisible)
        {
            showAlarmEditorItem.setText("Show Alarm Editor..."); 
            pane.remove(alarmEditorPanel);
            frame.setLayout(new GridLayout(1,1));
        }
        else
        {
            showAlarmEditorItem.setText("Hide Alarm Editor...");
            pane.remove(panel);
            frame.setLayout(new GridLayout(1,2));
            pane.add(alarmEditorPanel);
            pane.add(panel);
        }
        
        alarmEditorVisible = ! alarmEditorVisible;
        alarmEditorMenuItem.setState(alarmEditorVisible);
        
        /* Get the minimum width dependant on whether or not the alarm editor
         * is visible. */
        int actualMinWidth = alarmEditorVisible ? minWidth: minWidthNoAlarmEditor;
        
        //Maintain current window size as long as it is >= the minimum size.
        frame.setPreferredSize(frame.getSize());
        frame.setMinimumSize(new Dimension(actualMinWidth, minHeight));
        frame.pack();
    }
    
    /**
     * Shows a dialog asking the user to specify the parameters for a new alarm.
     * Returns null if the user clicks cancel.
     * 
     * @return The Alarm that the user specified or null.
     */
    public Alarm showAlarmDialog()
    {
        Calendar timePlusOneHour = Calendar.getInstance();
        timePlusOneHour.setTimeInMillis(
                timePlusOneHour.getTimeInMillis() + (1000 * 60 * 60));
        
        return showAlarmDialog(new Alarm("", timePlusOneHour));
    }
    
    
    /**
     * Shows a dialog asking the user to edit the alarm provided to this method.
     * Returns null if the user clicks cancel.
     * 
     * @param alarmToEdit The alarm that will be presented to the user to edit.
     * @return The Alarm the user edited or null.
     */
    public Alarm showAlarmDialog(Alarm alarmToEdit)
    {
        //Prepare the dialog then display it.
        alarmDialog.setAlarm(alarmToEdit);
        
        //If the user clicks cancel return null.
        return alarmDialog.show() ? alarmDialog.getAlarm() : null;
    }
    
    
    /**
     * Shows a dialog displaying the message provided with a yes and no option.
     * 
     * @param message The message to display to the user.
     * @return True if Yes option was selected.
     */
    public boolean showYesNoDialog(String message)
    {
        String buttons[] = {"Yes","No"};
        int result = JOptionPane.showOptionDialog(frame, message, "Clock",
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
            buttons, buttons[1]
        );
        
        return result == JOptionPane.YES_OPTION;
    }
    
    /**
     * Shows a dialog asking the user to select a file path to save to.
     * 
     * @return The File object referencing the path selected by the user or null.
     */
    public File showSaveFileDialog()
    {
        JFileChooser fileDialog = new JFileChooser();
        
        //Return null if the user cancels the operation.
        if(fileDialog.showSaveDialog(frame)!= JFileChooser.APPROVE_OPTION)
            return null;
        
        return fileDialog.getSelectedFile();
    }
    
    /**
     * Shows a dialog asking the user to select a file path to open from.
     * 
     * @return The File object referencing the path selected by the user or null.
     */
    public File showOpenFileDialog()
    {
        JFileChooser fileDialog = new JFileChooser();
        
        //Return null if the user cancels the operation.
        if(fileDialog.showOpenDialog(frame)!= JFileChooser.APPROVE_OPTION)
            return null;
        
        return fileDialog.getSelectedFile();
    }
    
    /**
     * Shows the user a popup box displaying the message provided.
     * 
     * @param message The message to show to the user.
     */
    public void displayPopupBox(String message)
    {
        JOptionPane.showMessageDialog(frame, message);
    }
    
    /**
     * Get the index of the item selected by the user in the alarm editor list.
     * 
     * @return The index of the item selected by the user in the alarm editor list.
     */
     public int getAlarmEditorSelectionIndex()
    {
        return alarmEditorList.getSelectedIndex();
    }
    
    
    /**
     * Unimplemented.
     */
    public void toggleDigitalAnalogue()
    {
        digitalClockDisplayed = !digitalClockDisplayed;
        clock24hMenuItem.setEnabled(digitalClockDisplayed);
    }
    
    /**
     * Clears the alarm editor list and repopulates it from the provided queue.
     * 
     * @param queue The queue to populate the alarm editor list with. 
     */
    public void updateAlarmEditor(PriorityQueue queue)
    {
        int preserveSelection = alarmEditorList.getSelectedIndex();
        
        listModel.clear();
        for(int i = 0; i < queue.getCount(); i++)
            listModel.add(i, queue.get(i).toString());
        
        alarmEditorList.setSelectedIndex(preserveSelection);
    }
    
    /**
     * Updates the position of the alarm hand on the clock panel.
     * 
     * @param alarmHour The hour the alarm hand should indicate.
     * @param alarmMinute The minute the alarm hand should indicate.
     */
    public void updateAlarmHand(int alarmHour, int alarmMinute)
    {
        panel.setAlarmTime(alarmHour, alarmMinute);
        panel.repaint();
    }
    
    /**
     * Updates the time displayed by the clock panel.
     * 
     * @param hour The hour the clock should indicate.
     * @param minute The minute the clock should indicate.
     * @param second The second the clock should indicate.
     */
    public void updateTime(int hour, int minute, int second)
    {
        panel.setTime(hour, minute, second);
        panel.repaint();
    }
}
