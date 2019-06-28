import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Ball implements IView {
    public static float vol = 0.3f ;
    public Point pos;
    float delta = 1000000; //mills
    public static int move_x = 1;
    public static int move_y = 1;
    public static int r = 10;
    public static int global_wei;
    public static int global_hei;


    public Ball(int w, int h){
        global_hei = h;
        global_wei = w;
        pos = new Point(global_wei/ 2, global_hei / 2);
    }



    public void delta_move(){

        //double time_diff = (System.nanoTime() - Game.last_move) / delta;
        double time_diff = 5;
        pos.translate((int)(time_diff * vol * move_x), (int)(time_diff *vol * move_y));

        /* When the ball hits the ceiling */
        if(pos.y <= 0){
            move_y *= -1;
            Game.game_score++;
            return;
        }
        /* When the ball hits the left/right wall */
        if(pos.x <= 0 || pos.x >= SplashScreen.gm.getWidth()){
            move_x *= -1;
            Game.game_score++;
            return;
        }
        /* When the ball go beyond the mover */
        if(pos.y >= SplashScreen.gm.getHeight() + Mover.height){
            Game.lives--;
            Game.paused = true;
            Game.temp_dead = true;
            pos = new Point(Game.wid / 2, Game.hei / 2);
            SplashScreen.gm.repaint();
            if(Game.lives <= 0){
                A2.sp.jf.setVisible(false);
                EndScreen ed = new EndScreen();
            }
            return;
        }

        Rectangle ball_area = new Rectangle(pos.x - r, pos.y - r, 2*r, 2*r);
        Boolean hitted = A2.sp.gm.mv.hit_test(ball_area);
        if(hitted){
            Game.game_score++;
            move_y *= -1;
        }

        for(int i = 0; i < Game.b_list.size();i++){
            Brick get_b = Game.b_list.get(i);
            get_b.rebound(ball_area);

            if(get_b.hitted){
                Game.b_list.remove(i);
            }

        }

        //SplashScreen.gm.repaint();


    }

    public void draw_ball(Graphics g2){
        g2.setColor(Color.YELLOW);
        g2.fillOval(pos.x - r, pos.y - r, 2*r, 2*r);
        System.out.println("Now the ball is at" + pos.x );
        System.out.println("Now the ball is at" + pos.y );


    }

    @Override
    public void updateView() {
        //delta_move();
    }
}
