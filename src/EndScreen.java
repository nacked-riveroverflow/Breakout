import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EtchedBorder;
import java.util.Date;


public class EndScreen extends JWindow {

    private static int counter;
    private static Timer timer;
    public static SplashScreen sp;
    public static Game gm;
    public static JFrame jf;


    public EndScreen() {
        Container ct = getContentPane();
        setSize(400, 500);

        JPanel panel = new JPanel();
        panel.setBorder(new EtchedBorder());
        panel.setLayout(null);
        panel.setBackground(Color.lightGray);

        ct.add(panel);

        JLabel label1 = new JLabel("I Hope You Enjoyed the Game");
        label1.setFont(new Font("Ariel", Font.BOLD, 20));
        label1.setBounds(40, 20, 300, 450);
        panel.add(label1);

        JLabel label2 = new JLabel("Your End Score: " + Game.game_score);
        label2.setBounds(40, 60, 300, 450);
        label2.setFont(new Font("Ariel", Font.BOLD, 18));
        label2.setForeground(Color.YELLOW);
        panel.add(label2);


        Date date = new Date();
        JLabel label3 = new JLabel("Date: " + date);
        label3.setFont(new Font("Ariel", Font.BOLD, 14));
        label3.setBounds(40, 80, 300, 450);
        panel.add(label3);

        JButton bt = new JButton();
        bt.setText("Exit");
        bt.setBounds(85, 350, 100, 20);
        bt.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                System.exit(0);
                ct.setVisible(false);
            }
        });
        panel.add(bt);


//        JLabel label1 = new JLabel("CS349 Breakout Game");
//        label1.setFont(new Font("Ariel",Font.BOLD,12));
//        panel.add(label1);
        setVisible(true);
        setLocationRelativeTo(null);
    }

}