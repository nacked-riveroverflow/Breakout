import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;


public class Game extends JPanel implements IView{

    //private Game gm;
    public Mover mv;
    public static boolean paused = false;
    public static int lives = 3;
    public static int wid;
    public static int hei;
    public static int game_score;
    public static Ball ball;
    public static Boolean running = false;
    public static Boolean temp_dead = false;
    public Model gameThread;
    /*Implemented two timers, one for ballmove(), another for repaint() */
    public static double last_move;
    public static double last_paint;
    public static int FPS = 25;

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
                    temp_dead = false;
                    repaint();//Check if this works
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
        pencil_image.getScaledInstance(getWidth(),getHeight(),pencil_image.SCALE_DEFAULT);
        System.out.println("Game Started");

        g.drawImage(pencil_image,1,1,pencil.getIconWidth(),pencil.getIconHeight(),this);
        g.setFont(new Font ("Arial", Font.BOLD, 20));
        if(!running){
            g.drawString("Press Space to Start", getWidth()/2 -20 , getHeight()/2 -20 );
        }else if (paused && !temp_dead){
            g.drawString("Game Paused: Will be back later", getWidth()/2 - 20, getHeight()/2 - 20);
            //Font
        }else if (paused && temp_dead){
            g.drawString("Game Paused: Press Esc to Continue", getWidth()/2 - 20, getHeight()/2 - 20);
            //Font
        }
        g.drawString("LIFE REMAINING: " + lives, 10, 20);
        g.drawString("FPS: " + FPS, 10, 40);
        g.drawString("Speed: " + Ball.vol, 10, 60);
        g.drawString("Score: " + game_score, 10, 80);
        mv.draw_mover(g);
        ball.draw_ball(g);

    }

    public void game_run(){
        //Rewrite
        if (gameThread != null) if(gameThread.isAlive()) gameThread.interrupt();

        reset();

        gameThread = new Model(this);

        gameThread.start();
    }

    public void reset(){
        mv = new Mover(getWidth(),getHeight());
        ball = new Ball(getWidth(),getHeight());
        game_score = 0;
        /*gameThread = new Model(this);
        gameThread.addView(ball);
        gameThread.addView(mv);
        gameThread.addView(this);*/
        //Reset breaksl
    }

    @Override
    public void updateView() {
        repaint();
    }
}
