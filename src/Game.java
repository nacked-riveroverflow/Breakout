import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;


public class Game extends JPanel {

    //private Game gm;
    public Mover mv;
    public static boolean paused = false;
    public static int lives = 3;
    public static int wid;
    public static int hei;
    public static Ball ball;
    public static Boolean running = false;
    public Model gameThread;
    public static double last_update;
    public static int FPS = 40;

    public Game(int widt, int heig){
        wid = widt;
        hei = heig;

        reset();
        this.setFocusable(true);

        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e){
                mv.left_pos.x = e.getX() - Mover.width /2;

                repaint();
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){/* Pause and Resume */
                    paused = !paused;
                } else if (e.getKeyCode() == KeyEvent.VK_Q){/*Quit the Game */
                    running = false;
                    System.exit(0);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !running){
                    running = true;
                    game_run();
                }
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
        pencil_image.getScaledInstance(wid,hei,pencil_image.SCALE_DEFAULT);
        System.out.println("Game Started");

        g.drawImage(pencil_image,1,1,pencil.getIconWidth(),pencil.getIconHeight(),this);
        mv.draw_mover(g);
        ball.draw_ball(g);

    }

    public void game_run(){
        //Rewrite
        if (gameThread != null) if(gameThread.isAlive()) gameThread.interrupt();
        reset();
        gameThread = new Model(this);
        gameThread.addView(ball);
        gameThread.addView(mv);
        gameThread.start();
    }

    public void reset(){
        mv = new Mover(wid,hei);
        ball = new Ball();
    }
}
