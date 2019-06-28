import java.awt.*;


public class Brick {
    public int wid;
    public int hei = 20;
    public Color b_color = Color.RED;
    private Game gm;
    public Point pos = new Point(1,1);
    public boolean hitted = false;

    public Brick(Game pgm){
        this.gm = pgm;
        wid = pgm.getSize().width / gm.num_per_row;
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
