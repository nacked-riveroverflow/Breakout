import javax.swing.JWindow;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;


public class A2
{
    public static SplashScreen sp;
    public static void main(String[]args)
    {
        int pass_fps = Integer.valueOf(args[0]);
        float pass_vol = Float.valueOf(args[1]);
        sp = new SplashScreen();
        sp.gm.ball.vol = pass_vol;
        sp.gm.FPS = pass_fps;

    }
}