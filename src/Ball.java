import java.awt.*;

public class Ball {
    float vol = 0.3f ;
    Point pos = new Point(Game.wid / 2, Game.hei / 2);
    float delta = 1 / 1000000; //mills
    public int move_x = 1;
    public int move_y = 1;
    public static int r = 10;

    public void delta_move(){
        double time_diff = (System.nanoTime() - Game.last_update) / delta;
        pos.translate((int)(time_diff * vol * move_x), (int)(time_diff *vol * move_y));

        /* When the ball hits the ceiling */
        if(pos.y <= 0){
            move_y *= -1;
            return;
        }
        /* When the ball hits the left/right wall */
        if(pos.x <= 0 || pos.x >= Game.wid){
            move_x *= -1;
            return;
        }
        /* When the ball go beyond the mover */
        if(pos.y >= Game.hei - 100 + Mover.height){
            Game.lives--;
            if(Game.lives <= 0){
                //quit
            }
        }

        Rectangle ball_area = new Rectangle(pos.x - r, pos.y - r, 2*r, 2*r);
        Boolean hitted = A2.sp.gm.mv.hit_test(ball_area);
        if(hitted){
            move_y *= -1;
        }

        SplashScreen.gm.repaint();


    }

    public void draw_ball(Graphics g2){
        g2.setColor(Color.YELLOW);
        g2.fillOval(pos.x - r, pos.y - r, 2*r, 2*r);
        System.out.println("Now the ball is at" + pos.x );
        System.out.println("Now the ball is at" + pos.y );


    }

}
