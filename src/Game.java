import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;


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
    public static Boolean new_lv = false;
    public static Boolean won_game = false;
    public Model gameThread;
    /*Implemented two timers, one for ballmove(), another for repaint() */
    public static double last_move;
    public static double last_paint;
    public static int FPS = 25;
    public static ArrayList<Brick> b_list;
    public static int level = 1;
    public int num_per_row = 2;

    public Game(int widt, int heig){
        wid = A2.sp.jf.getWidth();
        hei = A2.sp.jf.getHeight();

        reset();
        this.setFocusable(true);

        /**************************************************************************************************************
         **************************************************MOUSE and Key EVENTS****************************************
         **************************************************************************************************************/

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
                    new_lv = false;
                    repaint();//Check if this works
                } else if (e.getKeyCode() == KeyEvent.VK_Q){/*Quit the Game */
                    running = false;
                    A2.sp.jf.setVisible(false);
                    EndScreen ed = new EndScreen();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !running){
                    running = true;
                    game_run();
                }else if (e.getKeyCode() == KeyEvent.VK_2){
                    System.out.println("set to speed 2");
                    Ball.vol = 0.6f;
                }else if (e.getKeyCode() == KeyEvent.VK_1){
                    System.out.println("set to speed 1");
                    Ball.vol = 0.3f;
                }else if (e.getKeyCode() == KeyEvent.VK_3){
                    System.out.println("set to speed 3");
                    Ball.vol = 1f;
                /*Cheating Function */
                }else if (e.getKeyCode() == KeyEvent.VK_4){
                    System.out.println("set Paddle Size to 4");
                    Mover.width = A2.sp.jf.getSize().width / 10;
                }else if (e.getKeyCode() == KeyEvent.VK_5){
                    System.out.println("set Paddle Size to 5");
                    Mover.width = (int)(A2.sp.jf.getSize().width / 7.5);
                }else if (e.getKeyCode() == KeyEvent.VK_6){
                    System.out.println("set Paddle Size to 6");
                    Mover.width = (int)(A2.sp.jf.getSize().width / 5);
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        wid = A2.sp.jf.getWidth();
        hei = A2.sp.jf.getHeight();

        g.setColor(Color.lightGray);
        ImageIcon pencil = new ImageIcon(getClass().getResource("./backg.png"));
        Image pencil_image = pencil.getImage();
        pencil_image.getScaledInstance(getWidth(),getHeight(),pencil_image.SCALE_DEFAULT);
        System.out.println("Game Started");

        g.drawImage(pencil_image,1,1,pencil.getIconWidth(),pencil.getIconHeight(),this);
        g.setFont(new Font ("Arial", Font.BOLD, 20));
        if(!running){
            g.drawString("Press Space to Start", getWidth()/2 -20 , getHeight()/2 -20 );
        }else if (paused && !temp_dead && !new_lv){
            g.drawString("Game Paused: Will be back later", getWidth()/2 - 20, getHeight()/2 - 20);
            //Font
        }else if (paused && temp_dead){
            g.drawString("Game Paused: Press Esc to Continue", getWidth()/2 - 20, getHeight()/2 - 20);
            //Font
        }else if (paused && new_lv){
            g.drawString("Level Up!!: Press Esc to Continue", getWidth()/2 - 20, getHeight()/2 - 20);
            //Font
        }
        g.drawString("LIFE REMAINING: " + lives, 10, 20);
        g.drawString("FPS: " + FPS, 10, 40);
        g.drawString("Speed: " + Ball.vol, 10, 60);
        g.drawString("Score: " + game_score, 10, 80);
        g.drawString("Bricks Remained: " + b_list.size(), 10, 100);
        mv.draw_mover(g);
        ball.draw_ball(g);

        if(b_list.size() > 0){
              for(int i = 0; i < b_list.size(); i++){
                b_list.get(i).draw_brick(g);
            }
        }

    }

    public void game_run(){
        //Rewrite
        if (gameThread != null) if(gameThread.isAlive()) gameThread.interrupt();

        reset();

        gameThread = new Model(this);

        gameThread.start();
    }

    public void brick_line(int num){
        b_list = new ArrayList<Brick>();

        double single_wei = (0.8 * wid - 100) / num ;
        int gap = 10;
        int h = 30;
        for (int y=0; y<level; y++) {
            for (int x=0; x<num; x++) {
                Brick b = new Brick(this);
                b.b_color =  get_color_bylevel(y + 1);
                b.pos.x = (int) (x*(single_wei+gap)+wid / 5);
                b.pos.y =  (y*(h+gap)+50);
                System.out.println("The brick is at " + b.pos.x);
                b.hei =  h;
                b.wid = (int) single_wei;
                b_list.add(b);
            }
        }
    }

    public Color get_color_bylevel(int lv){
        if(lv == 1){
            return Color.RED;
        } else if (lv == 2){
            return Color.ORANGE;
        } else if (lv == 3){
            return Color.YELLOW;
        } else if (lv == 4){
            return Color.GREEN.darker();
        } else if (lv == 5){
            return Color.blue.brighter();
        } else {
            won_game = true;
            return Color.lightGray;
        }
    }


    public void reset(){
        mv = new Mover(getWidth(),getHeight());
        ball = new Ball(getWidth(),getHeight());
        game_score = 0;
        brick_line(num_per_row);
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
