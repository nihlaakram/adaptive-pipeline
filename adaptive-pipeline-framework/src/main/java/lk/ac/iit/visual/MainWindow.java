package lk.ac.iit.visual;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {
    //declare variable
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public MainWindow()//constructor
    {
        //set frame title
        setTitle("JPipe : Visualization ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 600);
        contentPane = new JPanel();

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel title = new JLabel("JPipe: Auto Scaling Framework for Pipeline Architecture");
        title.setFont(new Font("",Font.BOLD , 20));
        title.setBounds(27, 25, 800, 30);
        contentPane.add(title);

        JLabel subTitle = new JLabel("Performance Analysis Report");
        subTitle.setBounds(27, 55, 239, 20);
        contentPane.add(subTitle);


        //JFrame f=new JFrame();
        String data[][]={ {"101","Amit","670000"},
                {"102","Jai","780000"},
                {"101","Sachin","700000"},
                {"101","Sachin","700000"}};
        String column[]={"ID","NAME","SALARY"};
        JTable jt=new JTable(data,column);
        jt.setBounds(27,75,600,5*16);
        //ScrollPane.
        contentPane.add(jt);




        //view latency
        JButton btnNewFrame = new JButton("New");
        btnNewFrame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                LatencyWindow frame = new LatencyWindow();
            }
        });
        //btnNewFrame.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 12));
        btnNewFrame.setBounds(180, 195, 78, 25);
        contentPane.add(btnNewFrame);
    }

    /**
     * Launch the application.
     */
//main method
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
                    MainWindow frame = new MainWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


