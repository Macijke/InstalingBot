package online.macijke;

import javax.swing.*;
import java.awt.*;

public class Frame {
    public JFrame jFrame;
    public Frame() {
        jFrame = new JFrame("InstaLing BOT");
        jFrame.setSize(350, 500);
        jFrame.setLayout(new FlowLayout());
        jFrame.setBounds(0, 0, 350, 500);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel label1 = new JLabel("F4 aby rozpocząć pracę BOT'a");
        label1.setSize(350, 50);
        label1.setBounds(0,0, 350,50);
        jFrame.add(label1);

        JLabel label2 = new JLabel("F6 aby zakończyć pracę BOT'a");
        label2.setSize(350, 50);
        label2.setBounds(0,50, 350,50);
        jFrame.add(label2);

        JLabel label3 = new JLabel("");
        label3.setSize(350, 50);
        label3.setBounds(0,100, 350,50);
        jFrame.add(label3);
    }


}
