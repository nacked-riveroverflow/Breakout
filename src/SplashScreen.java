import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class SplashScreen extends JWindow
{

    private static int counter;
    private static Timer timer;
    public static SplashScreen sp;
    public static Game gm;
    public static JFrame jf;


    public SplashScreen()
    {
        Container ct = getContentPane();
        setSize(300,500);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.lightGray);

        ct.add(panel);

        JLabel label1 = new JLabel("CS349 Breakout Game");
        label1.setFont(new Font("Ariel",Font.BOLD,18));
        label1.setBounds(40,20,300,450);
        panel.add(label1);

        JLabel label2 = new JLabel("Author Name: Hezhi(Winston) Zhu");
        label2.setBounds(40,60,300,450);
        label2.setFont(new Font("Ariel",Font.BOLD,12));
        panel.add(label2);

        JLabel label3 = new JLabel("Made Date: 2019.06.20");
        label3.setFont(new Font("Ariel",Font.BOLD,12));
        label3.setBounds(40,70,300,450);
        panel.add(label3);

        JButton bt = new JButton();
        bt.setText("Continue");
        bt.setBounds(85,350,100,20);
        bt.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                game_wrapper();
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

    public void game_wrapper(){



        jf = new JFrame("Winston's Breakout Game");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setBackground(Color.lightGray);
        jf.setResizable(true);
        jf.setMinimumSize(new Dimension(1000,668));
        jf.setPreferredSize(new Dimension(1280,668));
        jf.setSize(new Dimension(1280,668));
        jf.setLayout(new BorderLayout());

        gm = new Game(jf.getSize().width,jf.getSize().height);
        //gm.refresh(jf.getSize().width,jf.getSize().height);
        jf.add(gm,BorderLayout.CENTER);

        jf.addComponentListener(new ResizeController());

    }

    private class ResizeController extends ComponentAdapter {

        public void componentResized(ComponentEvent e) {
            A2.sp.gm.refresh(e.getComponent().getWidth(),e.getComponent().getHeight());
        }
    }


}
