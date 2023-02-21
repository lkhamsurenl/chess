package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by luvsandondov on 9/19/14.
 */
public class GUIDraft extends JPanel implements MouseListener{
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //this.setBackground(new Color(153,67,24));
        // Add mouse listener event
        this.addMouseListener(this);
        //this.addMouseMotionListener(this);
        g.setColor(new Color(118,52,15));
        g.fillRect(0,0, 100, 100);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse Pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse Released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

/*    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }*/
}
