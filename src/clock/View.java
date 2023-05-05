package clock;

import java.awt.*;
import java.io.File;
import java.util.Calendar;
import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import javax.swing.filechooser.FileNameExtensionFilter;

public class View implements Observer {
    
    ClockPanel panel;
    Controller controller;
    Model model;
    
    AlarmDialog alarmDialog;
    
    //Menu bar menus and items
    //Indentation is used to indicate submenu items
    JMenu fileMenu;
        JMenuItem newMenuItem;
        JMenuItem openMenuItem;
        JMenuItem saveMenuItem;
        JMenuItem saveAsMenuItem;

    JMenu editMenu;
        JMenuItem addAlarmMenuItem;
        JMenuItem showAlarmEditorItem;
        JMenuItem resetAlarmsMenuItem;
        JMenuItem alarmSoundMenuItem;

    JMenu viewMenu;
        JCheckBoxMenuItem clock24hMenuItem;
        JCheckBoxMenuItem digitalClockMenuItem;
        JCheckBoxMenuItem alarmEditorMenuItem;

    JMenu aboutMenu;
        JMenuItem aboutAppMenuItem;
        JMenuItem aboutAuthorMenuItem;
    
    // Alarm Editor
    JPanel alarmEditorPanel;
        JScrollPane alarmEditorPane;
            JList alarmEditorList;
        JButton button;
        JButton button2;
        JButton button3;
    
    // Internal
    DefaultListModel listModel;
    
    JFrame frame;
    
    boolean alarmEditorVisible = false;
    boolean digitalClockDisplayed = false;
    boolean twentyFourHourMode = false;
    
    int minWidth;
    int minHeight;
    
    public View(Model model) {
        this.model = model;
    }
    
    public void init(Controller controller)
    {
        this.controller = controller;
        
        alarmDialog = new AlarmDialog();
        
        frame = new JFrame();
        frame.setLayout(new GridLayout(1,1));
        panel = new ClockPanel(model);
        //frame.setContentPane(panel);
        frame.setTitle("Clock");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(controller);
        
        // Start of border layout code
        
        // I've just put a single button in each of the border positions:
        // PAGE_START (i.e. top), PAGE_END (bottom), LINE_START (left) and
        // LINE_END (right). You can omit any of these, or replace the button
        // with something else like a label or a menu bar. Or maybe you can
        // figure out how to pack more than one thing into one of those
        // positions. This is the very simplest border layout possible, just
        // to help you get started.
        
        Container pane = frame.getContentPane();
        
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
        
        pane.add(panel);
        
        /* ----------------- Construct Alarm Editor ------------------------- */
        button = new JButton("Add");
        button2 = new JButton("Edit");
        button3 = new JButton("Remove");
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
        
        alarmEditorLayout.setHorizontalGroup(
            alarmEditorLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    
                .addComponent(alarmEditorPane)
                
                .addGroup( alarmEditorLayout.createSequentialGroup()
                    .addComponent(button)
                    .addComponent(button2)
                    .addComponent(button3)
                )
        );
        
        alarmEditorLayout.setVerticalGroup(
            alarmEditorLayout.createSequentialGroup()
                    
                .addComponent(alarmEditorPane).
                    
                addGroup( alarmEditorLayout.createParallelGroup()
                    .addComponent(button)
                    .addComponent(button2)
                    .addComponent(button3)
                )
        );
        
        alarmEditorPanel.add(alarmEditorPane);
        
        /* ---- Set controller as the action listener for all menu items ---- */
        
        
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
        
        button.addActionListener(controller);
        button2.addActionListener(controller);
        button3.addActionListener(controller);
        
        
        Insets insets = frame.getInsets();
        minWidth = (button.getMinimumSize().width + button2.getMinimumSize().width
            + button3.getMinimumSize().width + 30);
        minHeight = minWidth + insets.top + insets.bottom;
         
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        frame.setBounds(screenSize.width/2 - 250, screenSize.height/2 - 250, 500, 500);
        frame.setMinimumSize(new Dimension(minWidth, minHeight));
        frame.setPreferredSize(new Dimension(500,500));
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public void toggleAlarmEditorVisibility()
    {
        Container pane = frame.getContentPane();
        
        if(alarmEditorVisible)
        {
            pane.remove(alarmEditorPanel);
            frame.setLayout(new GridLayout(1,1));
        }
        else
        {
            pane.remove(panel);
            frame.setLayout(new GridLayout(1,2));
            pane.add(alarmEditorPanel);
            pane.add(panel);
        }
        
        alarmEditorVisible = ! alarmEditorVisible;
        
        /* Calculate the minimum width dependant on whether or not the alarm
         * editor is visible. */
        Insets insets = frame.getInsets();
        int actualMinWidth = insets.left + insets.right
                + (alarmEditorVisible ? minWidth * 2 : minWidth);
        
        //Maintain current window size as long as it is >= the minimum size.
        frame.setPreferredSize(frame.getSize());
        frame.setMinimumSize(new Dimension(actualMinWidth, minHeight));
        frame.pack();
    }
    
    public Alarm showAlarmDialog()
    {
        Calendar timePlusOneHour = Calendar.getInstance();
        timePlusOneHour.setTimeInMillis(
                timePlusOneHour.getTimeInMillis() + (1000 * 60 * 60));
        
        return showAlarmDialog(new Alarm("", timePlusOneHour));
    }
    
    public Alarm showAlarmDialog(Alarm alarmToEdit)
    {
        alarmDialog.setAlarm(alarmToEdit);
        
        int selection = alarmDialog.show();
        
        if(selection == JOptionPane.OK_OPTION)
            return alarmDialog.getAlarm();
        else
            return null;
    }
    
    public int getAlarmEditorSelectionIndex()
    {
        return alarmEditorList.getSelectedIndex();
    }
    
    
    public boolean promptYesNo(String message)
    {
        String ObjButtons[] = {"Yes","No"};
        int PromptResult = JOptionPane.showOptionDialog(frame,message,"Clock",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
        
        return PromptResult==JOptionPane.YES_OPTION;
    }
    
    public File showSaveFileDialog()
    {
        JFileChooser fileDialog = new JFileChooser();
        
        fileDialog.setFileFilter(new FileNameExtensionFilter("ICalendar File", "ical", "ics", "ifb", "icalendar"));
        if(fileDialog.showSaveDialog(frame)== JFileChooser.APPROVE_OPTION)
            return fileDialog.getSelectedFile();
        else
            return null;
    }
    public File showOpenFileDialog()
    {
        JFileChooser fileDialog = new JFileChooser();
        
        fileDialog.setFileFilter(new FileNameExtensionFilter("ICalendar File", "ical", "ics", "ifb", "icalendar"));
        if(fileDialog.showOpenDialog(frame)== JFileChooser.APPROVE_OPTION)
            return fileDialog.getSelectedFile();
        else
            return null;
    }
    
    public void toggleDigitalAnalogue()
    {
        digitalClockDisplayed = !digitalClockDisplayed;
        clock24hMenuItem.setEnabled(digitalClockDisplayed);
        
    }
    
    public void displayPopupBox(String message)
    {
        JOptionPane.showMessageDialog(frame, message);
    }
    
    public void update(Observable o, Object arg) {
        
        int preserveSelection = alarmEditorList.getSelectedIndex();
        if(model.qUpdated)
        {
            listModel.clear();
            for(int i = 0; i < model.q.getCount(); i++)
            {
                listModel.add(i,model.q.get(i).toString());
            }
            model.qUpdated = false;
        }
        panel.repaint();
        alarmEditorList.setSelectedIndex(preserveSelection);
        if(model.activatedAlarm != null)
        {
            JOptionPane.showMessageDialog(null, model.activatedAlarm.getMessage());
            model.activatedAlarm = null;
        }
    }
}
