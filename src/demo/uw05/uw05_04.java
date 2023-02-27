package demo.uw05;

import javax.swing.*;
import java.awt.*;

public class uw05_04 {
    private static int WIDTH = 400;
    private static int HEIGHT = 200;

    public static void main(String[] args) {
        JFrame jf = new JFrame("TWKS");

        jf.setSize(WIDTH, HEIGHT);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jp = new JPanel();



        Box hbox2 = Box.createHorizontalBox(); //水平箱2
        JLabel label2 = new JLabel("退勤しました。");
        hbox2.add(label2);

        Box hbox3 = Box.createHorizontalBox(); //水平箱4
        JLabel label3 = new JLabel("〇〇さん、今日はお疲れ様でした。");
        hbox3.add(label3);


        Box hbox6 = Box.createHorizontalBox(); //水平箱3
        JButton button3 = new JButton("終わり");
        hbox6.add(button3);


        Box vbox = Box.createVerticalBox(); //创建一个垂直箱
        Component vStrut1 = Box.createVerticalStrut(40);
        Component vStrut2 = Box.createVerticalStrut(10);
        Component vStrut3 = Box.createVerticalStrut(10);
        Component vStrut4 = Box.createVerticalStrut(10);

        vbox.add(vStrut1);
        vbox.add(hbox2);  //垂直箱加入一个水平箱2
        vbox.add(vStrut2);
        vbox.add(hbox3);  //垂直箱加入一个水平箱2
        vbox.add(vStrut3);
        vbox.add(hbox6);

        jp.add(vbox);
        jf.add(jp);
        jf.setVisible(true);  // 显示窗口


    }
}
