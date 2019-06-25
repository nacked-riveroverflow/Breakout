import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.*;

import static javax.imageio.ImageIO.read;

public class Game extends JPanel {

    //private Game gm;
    public Mover mv;

    public Game(){

        reset();
        this.setFocusable(true);

        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e){
                mv.left_pos.x = e.getX() - Mover.width /2;
                System.out.println("Now the mouse is at" + mv.left_pos.x );
                repaint();
            }
        });
    }

    /**************************************************************************************************************
 **************************************************MOUSE and Key EVENTS****************************************
 **************************************************************************************************************/



    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.lightGray);
        ImageIcon pencil = new ImageIcon(getClass().getResource("./backg.png"));
        Image pencil_image = pencil.getImage();
        pencil_image.getScaledInstance(1024,768,pencil_image.SCALE_DEFAULT);
        System.out.println("Game Started");

        g.drawImage(pencil_image,1,1,pencil.getIconWidth(),pencil.getIconHeight(),this);
        mv.draw_mover(g);
    }

    public void reset(){
        mv = new Mover();
    }
}
