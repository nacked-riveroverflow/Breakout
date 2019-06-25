import javax.swing.JWindow;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;


public class A2 extends JWindow
{

    public static void main(String[]args)
    {
        try {
            Splash sp = new Splash();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}