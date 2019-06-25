import java.awt.*;

public class Mover {
    public static int width =150;
    public static int height = 15;
    Point left_pos;

    public Mover(int window_wid, int window_hei){
        width = 150;
        height = 15;
        left_pos = new Point(0, window_hei- 20);

    }

    public void draw_mover(Graphics g2){
        g2.setColor(Color.lightGray);
        g2.fillRect(left_pos.x, left_pos.y-height/2 - 100, width, height);

    }

    public boolean hit_test(Rectangle rc){
        Rectangle mover_rec = new Rectangle(left_pos.x, left_pos.y-height/2 - 100, width, height);
        if(mover_rec.intersects(rc)){
            return true;
        } else {
            return false;
        }
    }
}
