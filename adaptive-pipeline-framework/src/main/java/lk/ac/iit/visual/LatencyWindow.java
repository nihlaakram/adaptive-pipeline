package lk.ac.iit.visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class LatencyWindow {
    //declare variable
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public LatencyWindow() //constructor
    {
        java.util.List<Double> scores = new ArrayList<>();

        java.util.List<Double> scores2 = new ArrayList<>();
        int maxDataPoints = 40;
        int maxScore = 10;
        for (int i = 0; i < maxDataPoints; i++) {
            scores.add((double) i);
            scores2.add((double) (i + 10));
//
        }


        JPanel mainPanel1 = new GraphPanel(scores);
        mainPanel1.setPreferredSize(new Dimension(800, 300));

        JFrame frame = new JFrame("DrawGraph");

        frame.getContentPane().add(mainPanel1);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}

/**public LatencyWindow() //constructor
    {
            setTitle("Latency");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            //set bounds of the frame
            setBounds(100, 100, 450, 300);

            contentPane = new JPanel();
            //set border
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            //set ContentPane
            setContentPane(contentPane);
            //set null
            contentPane.setLayout(null);

            //label in the frame
            JLabel lblWelcome = new JLabel("Welcome this is New Frame");
            //set fore ground color to the label
            lblWelcome.setForeground(Color.RED);
            //set font to the label
            lblWelcome.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
            //set bounds of the label
            lblWelcome.setBounds(75, 100, 294, 32);
            //add label to the contentPane
            contentPane.add(lblWelcome);
            }*/


class PanelExample {
    PanelExample()
    {
        Frame f= new Frame("Panel Example");
        Panel panel=new Panel();
        panel.setBounds(40,80,200,200);
        panel.setBackground(Color.gray);
        Button b1=new Button("Button 1");
        b1.setBounds(50,100,80,30);
        b1.setBackground(Color.yellow);
        Button b2=new Button("Button 2");
        b2.setBounds(100,100,80,30);
        b2.setBackground(Color.green);
        panel.add(b1); panel.add(b2);
        f.add(panel);
        f.setSize(400,400);
        f.setLayout(null);
        f.setVisible(true);
    }
    public static void main(String args[])
    {
        new PanelExample();
    }
}






