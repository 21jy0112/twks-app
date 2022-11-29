package demo.uw03;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class uw03_02 {
    private static int WIDTH = 400;
    private static int HEIGHT = 200;

    public static void main(String[] args) {
        JFrame jf = new JFrame("TWKS");

        jf.setSize(WIDTH, HEIGHT);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jp = new JPanel();

        Box hbox1 = Box.createHorizontalBox(); //水平箱1
        Component hStrut1 = Box.createHorizontalStrut(300);//一个20单位不可见组件
        Border blackline = BorderFactory.createLineBorder(Color.green);
        JLabel label1 = new JLabel("休憩中");
        label1.setBorder(blackline);
        hbox1.add(hStrut1);
        hbox1.add(label1); //水平箱加入一个标签


        Box hbox2 = Box.createHorizontalBox(); //水平箱2
        JLabel label2 = new JLabel("〇〇さん、お疲れ様です。");
        hbox2.add(label2);

        Box hbox3 = Box.createHorizontalBox(); //水平箱4
        JLabel label3 = new JLabel("休憩中です！");
        hbox3.add(label3);

        Box hbox4 = Box.createHorizontalBox(); //水平箱4
        JLabel label4 = new JLabel("今日実働時間:　2時間30分");
        hbox4.add(label4);

        Box hbox5 = Box.createHorizontalBox(); //水平箱4
        JLabel label5 = new JLabel("今までの休憩時間：1時05分");
        hbox5.add(label5);

        Box hbox6 = Box.createHorizontalBox(); //水平箱3
        JButton button3 = new JButton("休憩終了");
        hbox6.add(button3);


        Box vbox = Box.createVerticalBox(); //创建一个垂直箱
        Component vStrut1 = Box.createVerticalStrut(20);
        Component vStrut2 = Box.createVerticalStrut(10);
        Component vStrut3 = Box.createVerticalStrut(10);
        Component vStrut4 = Box.createVerticalStrut(10);
        vbox.add(hbox1);   //垂直箱加入一个水平箱1
        vbox.add(vStrut1);
        vbox.add(hbox2);  //垂直箱加入一个水平箱2
        vbox.add(hbox3);  //垂直箱加入一个水平箱2
        vbox.add(vStrut3);
        vbox.add(hbox4);  //垂直箱加入一个水平箱3
        vbox.add(hbox5);
        vbox.add(vStrut4);
        vbox.add(hbox6);

        jp.add(vbox);
        jf.add(jp);
        jf.setVisible(true);  // 显示窗口


    }
}
