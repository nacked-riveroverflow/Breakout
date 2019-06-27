import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import java.util.ArrayList;

import java.util.Observer;

public class Model extends Thread {
    public Game gm;
    private ArrayList<IView> views = new ArrayList<IView>();

    public Model(Game gm){
        this.gm = gm;
    }

    public void addView(IView view) {
        views.add(view);
        // update the view to current state of the model
        view.updateView();
    }

    public void run(){
        gm.last_update = System.nanoTime();
        while(Game.running){
            Game.ball.delta_move();
            gm.last_update = System.nanoTime();
            try {
                Thread.sleep( (long) (1000/ Game.FPS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
