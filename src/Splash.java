import javax.swing.JWindow;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

import static javax.imageio.ImageIO.read;


public class Splash extends JWindow
{
    BufferedImage img = read(new File("images/pipu.jpg"));


    public Splash() throws IOException {
        try
        {
            setSize(1024,768);
            setLocationRelativeTo(null);
           // g.drawImage(img,0,0,null);
            Thread.sleep(3000);
            dispose();
            javax.swing.JOptionPane.showMessageDialog((java.awt.Component)
                            null,"Welcome to the break Game", "Welcome Screen:",
                    javax.swing.JOptionPane.DEFAULT_OPTION);
        }
        catch(Exception exception)
        {
            javax.swing.JOptionPane.showMessageDialog((java.awt.Component)
                            null,"Error"+exception.getMessage(), "Error:",
                    javax.swing.JOptionPane.DEFAULT_OPTION);
        }
    }

    public void paint(Graphics g)
    {
        g.drawImage(img,0,0,null);
    }

}