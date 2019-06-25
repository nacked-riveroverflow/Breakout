import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

import static javax.imageio.ImageIO.read;

public class Game extends JPanel {

    //private Game gm;


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.lightGray);
        ImageIcon pencil = new ImageIcon(getClass().getResource("./oval.png"));
        Image pencil_image = pencil.getImage();
        pencil_image.getScaledInstance(1024,768,pencil_image.SCALE_DEFAULT);
        System.out.println("Game Started");

        g.drawImage(pencil_image,1,1,pencil.getIconWidth(),pencil.getIconHeight(),this);
    }
}
