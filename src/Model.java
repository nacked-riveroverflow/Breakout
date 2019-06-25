

public class Model extends Thread {
    public Game gm;

    public Model(Game gm){
        this.gm = gm;
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
