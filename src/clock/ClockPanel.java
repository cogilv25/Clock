package clock;

import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import static java.lang.Float.max;
import javax.swing.*;

/**
 * The class that draws the clock.
 * 
 * @author Calum Lindsay
 */
public class ClockPanel extends JPanel {
    
    /**
     * The hour that the clock displays.
     */
    private int hour;
    
    /**
     * The minute that the clock displays.
     */
    private int minute;
    
    /**
     * The second that the clock displays.
     */
    private int second;
    
    /**
     * The alarm hour that the clock displays.
     */
    private int alarmHour;
    
    /**
     * The alarm minute that the clock displays.
     */
    private int alarmMinute;
    
    /**
     * Creates a new ClockPanel instance.
     */
    public ClockPanel() 
    {
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.white);
        alarmHour = -1;
    }
    
    /**
     * Sets the time to display on the clock the next time it is drawn.
     * 
     * @param hour The hour to display on the clock.
     * @param minute The minute to display on the clock.
     * @param second The second to display on the clock.
     */
    public void setTime(int hour, int minute, int second)
    {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }
    
    /**
     * Sets the alarm time to display on the clock the next time it is drawn.
     * 
     * @param alarmHour The alarm hour to display on the clock.
     * @param alarmMinute  The alarm minute to display on the clock.
     */
    public void setAlarmTime(int alarmHour, int alarmMinute)
    {
        this.alarmHour = alarmHour;
        this.alarmMinute = alarmMinute;
    }
    
    /**
     * Draws the clock.
     * 
     * @param g the graphics context to use.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Rectangle bounds = getBounds();
        
        Graphics2D gg = (Graphics2D) g;
        int x0 = bounds.width / 2;
        int y0 = bounds.height / 2;
        
        int size = Math.min(x0, y0);
        
        gg.setStroke(new BasicStroke(1));
        
        double radius = 0;
        double theta = 0;
        
        // Draw the tick marks around the outside
        for (int n = 0; n < 60; n++) {
            theta = (90 - n * 6) / (180 / Math.PI);
            if (n % 5 == 0) {
                radius = 0.65 * size;
            } else {
                radius = 0.7 * size;
            }
            double x1 = x0 + radius * Math.cos(theta);
            double y1 = y0 - radius * Math.sin(theta);
            radius = 0.75 * size;
            double x2 = x0 + radius * Math.cos(theta);
            double y2 = y0 - radius * Math.sin(theta);
            gg.draw(new Line2D.Double(x1, y1, x2, y2));
        }
        
        // Draw the numbers
        Font font = new Font("SansSerif", Font.PLAIN, size / 5);
        gg.setFont(font);
        for (int n = 1; n <= 12; n++) {
            theta = (90 - n * 30) / (180 / Math.PI);
            radius = 0.9 * size;
            double x1 = x0 + radius * Math.cos(theta);
            double y1 = y0 - radius * Math.sin(theta);
            String s = "" + n;
            // To centre the numbers on their places, we need to get
            // the exact dimensions of the box
            FontRenderContext context = gg.getFontRenderContext();
            Rectangle2D msgbounds = font.getStringBounds(s, context);
            double ascent = -msgbounds.getY();
            double descent = msgbounds.getHeight() + msgbounds.getY();
            double height = msgbounds.getHeight();
            double width = msgbounds.getWidth();
            
            gg.drawString(s, (new Float(x1 - width/2)).floatValue(), 
                          (new Float(y1 + height/2 - descent)).floatValue());
        }
        
        double x1, y1;
        // Draw the alarm hand I want this under all other hands so it has to
        // be drawn first.
        if(alarmHour >= 0)
        {
            gg.setColor(Color.blue);
            gg.setStroke(new BasicStroke(max(0.01f*size,1.5f)));
            theta = (90 - (alarmHour + (alarmMinute / 60.0)) * 30) / (180 / Math.PI);
            radius = 0.6 * size;
            x1 = x0 + radius * Math.cos(theta);
            y1 = y0 - radius * Math.sin(theta);
            gg.draw(new Line2D.Double(x0, y0, x1, y1));

            //Reset the draw color to black
            gg.setColor(Color.black);
        }
        
        // Draw the hour hand
        gg.setStroke(new BasicStroke(max(0.012f*size,2.0f)));
        theta = (90 - (hour + minute / 60.0) * 30) / (180 / Math.PI);
        radius = 0.5 * size;
        x1 = x0 + radius * Math.cos(theta);
        y1 = y0 - radius * Math.sin(theta);
        gg.draw(new Line2D.Double(x0, y0, x1, y1));
        
        // Draw the minute hand
        gg.setStroke(new BasicStroke(max(0.006f*size,1.0f)));
        theta = (90 - (minute + second / 60.0) * 6) / (180 / Math.PI);
        radius = 0.75 * size;
        x1 = x0 + radius * Math.cos(theta);
        y1 = y0 - radius * Math.sin(theta);
        gg.draw(new Line2D.Double(x0, y0, x1, y1));
        
        // Draw the second hand
        gg.setColor(Color.red);
        gg.setStroke(new BasicStroke(0.003f*size));
        theta = (90 - second * 6) / (180 / Math.PI);
        x1 = x0 + radius * Math.cos(theta);
        y1 = y0 - radius * Math.sin(theta);
        gg.draw(new Line2D.Double(x0, y0, x1, y1));
    }
}
