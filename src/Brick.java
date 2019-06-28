import java.awt.*;


public class Brick {
    public static int wid;
    public static int hei ;
    public static int gap ;
    public Color b_color = Color.RED;
    private Game gm;
    public Point pos = new Point(1,1);
    public boolean hitted = false;
    public boolean dead = false;

    public Brick(Game pgm){
        this.gm = pgm;
        wid = pgm.getSize().width / gm.num_per_row;
    }

    private void set_dead(){
        dead = true;
    }

    public void rebound(Rectangle ball_rec){

        Rectangle top_layer = new Rectangle(pos.x, pos.y, wid, hei/20);
        Rectangle left_layer = new Rectangle(pos.x, pos.y, wid / 20, hei);
        Rectangle right_layer = new Rectangle(pos.x + (19 * wid ) / 20, pos.y, wid / 20, hei);
        Rectangle bot_layer = new Rectangle(pos.x, pos.y + (19 * hei) / 20, wid, hei/20);

        if(left_layer.intersects(ball_rec) || right_layer.intersects(ball_rec)){
            hitted = true;
            gm.game_score += 3;
            Ball.move_x *= -1;
        }

        if(top_layer.intersects(ball_rec) || bot_layer.intersects(ball_rec)){
            hitted = true;
            gm.game_score+= 3;
            Ball.move_y *= -1;

        }
    }

    public void draw_brick(Graphics g2){
        g2.setColor(b_color);
        g2.fillRect(pos.x,pos.y,wid,hei);

        /*This piece of code is found online for printing a brick like shape with
        * gradual changes */

        for (int i=0; i< hei/4; i++) {
            g2.setColor(b_color.darker());
            g2.drawLine(pos.x+i, pos.y+hei-i, pos.x+wid-1, pos.y+hei-i);
            g2.drawLine(pos.x+wid-1-i, pos.y+i, pos.x+wid-1-i, pos.y+hei);

            g2.setColor(b_color.brighter());
            g2.drawLine(pos.x, pos.y+i, pos.x+wid-1-i, pos.y+i);
            g2.drawLine(pos.x+i, pos.y+hei-i, pos.x+i, pos.y);

        }



    }

}
