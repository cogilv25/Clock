package clock;

import java.awt.*;
import javax.swing.*;
import java.util.Observer;
import java.util.Observable;

public class View implements Observer {
    
    ClockPanel panel;
    Controller controller;
    Model model;
    
    
    //Indentation used to indicate sub-items
    JMenu fileMenu;
        JMenuItem newMenuItem;
        JMenuItem openMenuItem;
        JMenuItem saveMenuItem;
        JMenuItem saveAsMenuItem;

    JMenu editMenu;
        JMenuItem addAlarmMenuItem;
        JMenu editAlarmSubMenu;
            JMenuItem editAlarmItem1;
            JMenuItem editAlarmItem2;
            JMenuItem editAlarmItem3;
            JMenuItem showAlarmEditorItem;
            
        JMenuItem resetAlarmsMenuItem;
        JMenuItem alarmSoundMenuItem;

    JMenu viewMenu;
        JCheckBoxMenuItem clock24hMenuItem;
        JCheckBoxMenuItem digitalClockMenuItem;
        JCheckBoxMenuItem alarmEditorMenuItem;

    JMenuItem aboutMenuItem;
    
    // Alarm Editor
    JPanel alarmEditorPanel;
        JScrollPane alarmEditorPane;
            JList alarmEditorList;
        JButton button;
        JButton button2;
    
    // Internal
    DefaultListModel listModel;
    
    JFrame frame;
    
    boolean alarmEditorVisible = false;
    
    public View(Model model) {
        this.model = model;
    }
    
    public void init(Controller controller)
    {
        this.controller = controller;
        
        frame = new JFrame();
        frame.setLayout(new GridLayout(1,1));
        panel = new ClockPanel(model);
        //frame.setContentPane(panel);
        frame.setTitle("Java Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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
        
        /* Edit Alarm Sub Menu TODO: Populate with active alarms. */
        editAlarmSubMenu = new JMenu("Edit Alarm");
        editAlarmItem1 = new JMenuItem("Alarm 1");
        editAlarmItem2 = new JMenuItem("Alarm 2");
        editAlarmItem3 = new JMenuItem("Alarm 3");
        showAlarmEditorItem = new JMenuItem("Show Alarm Editor...");
        editAlarmSubMenu.add(editAlarmItem1);
        editAlarmSubMenu.add(editAlarmItem2);
        editAlarmSubMenu.add(editAlarmItem3);
        editAlarmSubMenu.add(showAlarmEditorItem);
        
        resetAlarmsMenuItem = new JMenuItem("Reset All Alarms");
        alarmSoundMenuItem = new JMenuItem("Set Alarm Sound");
        editMenu.add(addAlarmMenuItem);
        editMenu.add(editAlarmSubMenu);
        editMenu.add(resetAlarmsMenuItem);
        editMenu.add(alarmSoundMenuItem);
        menuBar.add(editMenu);
        
        /* ------------------- Construct View Menu -------------------------- */
        viewMenu = new JMenu("View");
        clock24hMenuItem = new JCheckBoxMenuItem("24h Clock");
        digitalClockMenuItem = new JCheckBoxMenuItem("Digital Clock");
        alarmEditorMenuItem = new JCheckBoxMenuItem("Alarm Editor");
        viewMenu.add(clock24hMenuItem);
        viewMenu.add(digitalClockMenuItem);
        viewMenu.add(alarmEditorMenuItem);
        menuBar.add(viewMenu);
        
        /* Finally add an about menu item to the menubar */
        aboutMenuItem = new JMenu("About");
        menuBar.add(aboutMenuItem);
        
        frame.setJMenuBar(menuBar);
        
        /* ----------------- Construct Alarm Editor ------------------------- */
        button = new JButton("Edit");
        button2 = new JButton("Delete");
        alarmEditorPanel = new JPanel();
        listModel = new DefaultListModel();
        alarmEditorList = new JList(listModel);
        alarmEditorPane = new JScrollPane(alarmEditorList);
        //alarmEditorPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        alarmEditorPanel.add(alarmEditorPane);
        
        /*gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        alarmEditorPanel.add(button, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        alarmEditorPanel.add(button2, gbc);*/
        
        
        listModel.addElement("Hello");
        listModel.addElement("There");
        listModel.addElement("Sir");
        listModel.addElement("Jimmy");
        listModel.addElement("He2llo");
        listModel.addElement("The2re");
        listModel.addElement("Si1r");
        listModel.addElement("Ji3mmy");
        listModel.addElement("He4llo");
        listModel.addElement("Th2ere");
        listModel.addElement("Si5r");
        listModel.addElement("Ji7mmyJi7mmy");
        listModel.addElement("The2reThe2re");
        listModel.addElement("Si1rThe2re");
        listModel.addElement("Ji3mThe2remy");
        listModel.addElement("He4The2rello");
        listModel.addElement("TThe2reh2ere");
        listModel.addElement("Si5The2re");
        listModel.addElement("Ji7mThe2remy");
        
        /* ---- Set controller as the action listener for all menu items ---- */
        
        
        newMenuItem.addActionListener(controller);
        openMenuItem.addActionListener(controller);
        saveMenuItem.addActionListener(controller);
        saveAsMenuItem.addActionListener(controller);
        
        addAlarmMenuItem.addActionListener(controller);
        editAlarmItem1.addActionListener(controller);
        editAlarmItem2.addActionListener(controller);
        editAlarmItem3.addActionListener(controller);
        showAlarmEditorItem.addActionListener(controller);
        resetAlarmsMenuItem.addActionListener(controller);
        alarmSoundMenuItem.addActionListener(controller);
        
        clock24hMenuItem.addActionListener(controller);
        digitalClockMenuItem.addActionListener(controller);
        alarmEditorMenuItem.addActionListener(controller);
        
        aboutMenuItem.addActionListener(controller);
        
         
        panel.setPreferredSize(new Dimension(200, 200));
        pane.add(panel);
        
        // End of borderlayout code
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public void toggleAlarmEditorVisibility()
    {
        Container pane = frame.getContentPane();
        
        if(alarmEditorVisible)
        {
            pane.remove(alarmEditorPane);
            frame.setLayout(new GridLayout(1,1));
        }
        else
        {
            pane.remove(panel);
            frame.setLayout(new GridLayout(1,2));
            pane.add(alarmEditorPane);
            pane.add(panel);
        }
        
        alarmEditorVisible = ! alarmEditorVisible;
        frame.setMinimumSize(frame.getSize());
        frame.pack();
        frame.setMinimumSize(null);
    }
    
    public void update(Observable o, Object arg) {
        panel.repaint();
        if(model.activatedAlarm != null)
        {
            JOptionPane.showMessageDialog(null, model.activatedAlarm.getMessage());
            model.activatedAlarm = null;
        }
    }
}
