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
    public static Boolean cheat_mode = false;
    public Model gameThread;
    /*Implemented two timers, one for ballmove(), another for repaint() */
    public static double last_move;
    public static double last_paint;
    public static int FPS = 25;
    public static ArrayList<Brick> b_list;
    public static int level = 1;
    public int num_per_row = 10;


    /*For Resizing */
    public int SCALEX = (int) Math.ceil((double) wid / 900);
    public int SCALEY = (int) Math.ceil((double) wid / 900);

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
                }else if (e.getKeyCode() == KeyEvent.VK_4 && cheat_mode){
                    System.out.println("set Paddle Size to 4");
                    Mover.width = A2.sp.jf.getSize().width / 10;
                }else if (e.getKeyCode() == KeyEvent.VK_5 && cheat_mode){
                    System.out.println("set Paddle Size to 5");
                    Mover.width = (int)(A2.sp.jf.getSize().width / 7.5);
                }else if (e.getKeyCode() == KeyEvent.VK_6 && cheat_mode){
                    System.out.println("set Paddle Size to 6");
                    Mover.width = (int)(A2.sp.jf.getSize().width / 5);
                }else if (e.getKeyCode() == KeyEvent.VK_7 && cheat_mode){
                    System.out.println("set Ball Size to 7");
                    Ball.r = 10;
                }else if (e.getKeyCode() == KeyEvent.VK_8 && cheat_mode){
                    System.out.println("set Ball Size to 8");
                    Ball.r = 25;
                }else if (e.getKeyCode() == KeyEvent.VK_9 && cheat_mode){
                    System.out.println("set Ball Size to 9");
                    Ball.r = 40;
                }else if (e.getKeyCode() == KeyEvent.VK_C){
                    System.out.println("Cheat Model is on");
                    cheat_mode = !cheat_mode;
                }else if (e.getKeyCode() == KeyEvent.VK_N && cheat_mode && level != 6){
                    System.out.println("Cheat: Go to the next level");
                    level++;
                    brick_line();
                    paused = !paused;
                    Game.ball.pos = new Point(Game.wid/2, Game.hei /2);
                    if(Ball.move_y < 0){
                        Ball.move_y *= -1;
                    }
                    new_lv = true;
                }else if (e.getKeyCode() == KeyEvent.VK_A && cheat_mode){
                    System.out.println("Add one extra live");
                    lives++;
                } else if (e.getKeyCode() == KeyEvent.VK_S){
                    System.out.println("Make Screenshot");
                    BufferedImage img = getScreenShot(SplashScreen.jf);
                    CopyImagetoClipBoard ccb = new CopyImagetoClipBoard(img);
                }
            }
        });
    }

    /**************************************************************************************************************
     **************************************************Painting Feature****************************************
     **************************************************************************************************************/
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        wid = A2.sp.jf.getWidth();
        hei = A2.sp.jf.getHeight();

        g.setColor(Color.lightGray);
        ImageIcon pencil = new ImageIcon(getClass().getResource("backg.png"));
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
        }else if (cheat_mode){
            g.setFont(new Font ("Arial", Font.BOLD, 20));
            g.setColor(Color.RED);
            g.drawString("Cheat Mode is on :)", getWidth()/2 , 20);
            //Font
        }
        g.setColor(Color.lightGray);
        g.setFont(new Font ("Arial", Font.BOLD, 20));
        g.drawString("LIFE REMAINING: " + lives, 10, 20);
        g.drawString("FPS: " + FPS, 10, 40);
        g.drawString("Speed: " + Ball.vol, 10, 60);
        g.drawString("Score: " + game_score, 10, 80);
        g.drawString("Bricks Remained: " + get_remain(), 10, 100);
        g.drawString("Current at Level: " + level, 10, 120);
        mv.draw_mover(g);
        ball.draw_ball(g);

        if(running) {
            if (b_list.size() > 0) {
                for (int i = 0; i < b_list.size(); i++) {
                    //b_list.get(i).wid = (int) ((0.8 * getWidth() - 100) / num_per_row);
                    //int ori_gap = b_list.get(i).gap;
                    //int ori_width =b_list.get(i).gap * 20;
                    //b_list.get(i).gap = b_list.get(i).wid/20;

                    //b_list.get(i).pos.x += (int) ((getWidth() - ori_width));
                    if(!b_list.get(i).dead) {
                        b_list.get(i).draw_brick(g);
                    }
                    //check
                }
            }
        }

    }

    /**************************************************************************************************************
     **************************************************Helper Function****************************************
     **************************************************************************************************************/

    public int get_remain(){
        int re = 0;
        for(int i = 0; i < b_list.size(); i++){
            if(!b_list.get(i).dead){
                re++;
            }
        }
        return re;
    }

    public void game_run(){
        //Rewrite
        if (gameThread != null) if(gameThread.isAlive()) gameThread.interrupt();

        reset();

        gameThread = new Model(this);

        gameThread.start();
    }

    public void brick_line(){
        b_list = new ArrayList<Brick>();

        //double single_wei = (0.8 * wid - 100) / num_per_row ;
        double single_wei = Brick.wid;
        int gap = Brick.gap;
        //int h = 30;
        for (int y=0; y<level; y++) {
            for (int x=0; x<num_per_row; x++) {
                Brick b = new Brick(this);
                b.b_color =  get_color_bylevel(y + 1);
                b.pos.x = (int) (x*(single_wei+gap)+wid / 5);
                b.pos.y =  (y*(Brick.hei+gap)+50);
                System.out.println("The brick is at " + b.pos.x);
                //b.hei =  h;
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
        } else if (lv == 6){
            return Color.PINK.darker();
        } else {
            won_game = true;
            return Color.lightGray;
        }
    }

    /**************************************************************************************************************
     **************************************************Refreshing Function****************************************
     **************************************************************************************************************/

    public void reset(){
        mv = new Mover(getWidth(),getHeight());
        ball = new Ball(getWidth(),getHeight());
        game_score = 0;
        brick_line();
    }

    public void refresh(int width,int height) {

        /*Ball follow wall*/
        if(ball.pos.x > width){
            ball.pos.x = width;
            ball.move_x *= -1;
        }

        this.wid=width;
        this.hei=height;

        SCALEX = (int) Math.ceil((double) width / 100);
        SCALEY = (int) Math.ceil((double) height / 900);
        Brick.wid = 6 * SCALEX;
        Brick.hei = 35 * SCALEY;
        Brick.gap = Brick.wid / 5;
        if(!b_list.isEmpty()){
            resize_allb(Brick.wid,Brick.hei,Brick.wid / 5);
        }


    }

    @Override
    public void updateView() {
        repaint();
    }

    public void resize_allb(int w, int h, int g){
        int counterb = 0;
        for (int y=0; y<level; y++) {
            for (int x=0; x<num_per_row; x++) {
                Brick b = b_list.get(counterb);
                b.wid = w;
                b.pos.x = (int) (x*(w+g)+ wid / 5);
               counterb++;
            }
        }
        last_paint = System.nanoTime();
        repaint();
    }

    public static BufferedImage getScreenShot(Component component)    //used to get the current image drawn on the screen
    {
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());   // paints into image's Graphics
        return image;
    }
}
