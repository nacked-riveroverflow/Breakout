import java.awt.*;

public class Ball {
    float vol = 0.2f * 50 / 200;
    Point pos = new Point(A2.sp.gm.getWidth() / 2,A2.sp.gm.getHeight() / 2);
    float delta = 1 / 1000000; //mills
    public int move_x = 1;
    public int move_y = 1;

    public void delta_move(){
        pos.translate((int)(delta * vol * move_x), (int)(delta *vol * move_y));

        /* When the ball hits the ceiling */
        if(pos.y <= 0){
            move_y *= -1;
            return;
        }
        /* When the ball hits the left/right wall */
        if(pos.x <= 0 || pos.x >= 1280){
            move_x *= -1;
            return;
        }
        /* When the ball go beyond the mover */
        if(pos.y >= 668){
            Game.lives--;
            if(Game.lives <= 0){
                //quit
            }
        }


    }

}
