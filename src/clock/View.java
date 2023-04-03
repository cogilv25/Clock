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
            JMenuItem tempFakeAlarmItem1;
            JMenuItem tempFakeAlarmItem2;
            JMenuItem tempFakeAlarmItem3;
            JMenuItem showAlarmEditorItem;
        JMenuItem resetAlarmsMenuItem;
        JMenuItem alarmSoundMenuItem;

    JMenu viewMenu;
        JCheckBoxMenuItem clock24hMenuItem;
        JCheckBoxMenuItem digitalClockMenuItem;
        JCheckBoxMenuItem alarmEditorMenuItem;

    JMenuItem aboutMenuItem;
    
    public View(Model model) {
        this.model = model;
    }
    
    public void init(Controller controller)
    {
        this.controller = controller;
        
        JFrame frame = new JFrame();
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
        tempFakeAlarmItem1 = new JMenuItem("Alarm 1");
        tempFakeAlarmItem2 = new JMenuItem("Alarm 2");
        tempFakeAlarmItem3 = new JMenuItem("Alarm 3");
        showAlarmEditorItem = new JMenuItem("Show Alarm Editor...");
        editAlarmSubMenu.add(tempFakeAlarmItem1);
        editAlarmSubMenu.add(tempFakeAlarmItem2);
        editAlarmSubMenu.add(tempFakeAlarmItem3);
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
        
        /* ---- Set controller as the action listener for all menu items ---- */
        
        
        newMenuItem.addActionListener(controller);
        openMenuItem.addActionListener(controller);
        saveMenuItem.addActionListener(controller);
        saveAsMenuItem.addActionListener(controller);
        
        addAlarmMenuItem.addActionListener(controller);
        tempFakeAlarmItem1.addActionListener(controller);
        tempFakeAlarmItem2.addActionListener(controller);
        tempFakeAlarmItem3.addActionListener(controller);
        showAlarmEditorItem.addActionListener(controller);
        resetAlarmsMenuItem.addActionListener(controller);
        alarmSoundMenuItem.addActionListener(controller);
        
        clock24hMenuItem.addActionListener(controller);
        digitalClockMenuItem.addActionListener(controller);
        alarmEditorMenuItem.addActionListener(controller);
        
        aboutMenuItem.addActionListener(controller);
        
        JButton button = new JButton("Button 1 (PAGE_START)");
        pane.add(button, BorderLayout.PAGE_START);
         
        panel.setPreferredSize(new Dimension(200, 200));
        pane.add(panel, BorderLayout.CENTER);
         
        button = new JButton("Button 3 (LINE_START)");
        pane.add(button, BorderLayout.LINE_START);
         
        button = new JButton("Long-Named Button 4 (PAGE_END)");
        pane.add(button, BorderLayout.PAGE_END);
         
        button = new JButton("5 (LINE_END)");
        pane.add(button, BorderLayout.LINE_END);
        
        // End of borderlayout code
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public void update(Observable o, Object arg) {
        panel.repaint();
    }
}
