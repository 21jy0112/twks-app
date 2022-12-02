package demo.uw01;

import javax.swing.*;
import java.awt.*;

public class uw01_01 {
    private static int WIDTH = 400;
    private static int HEIGHT = 200;

    public static void main(String[] args) {
        JFrame jf = new JFrame("TWKS");

        jf.setSize(WIDTH, HEIGHT);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jp = new JPanel();

        Box hbox1 = Box.createHorizontalBox(); //水平箱1
        JLabel label1 = new JLabel("メールアドレス");
        JTextField username = new JTextField(10);
        Component hStrut1 = Box.createHorizontalStrut(5);//一个20单位不可见组件
        hbox1.add(label1); //水平箱加入一个标签
        //hbox1.add(hStrut1);
        hbox1.add(username);


        Box hbox2 = Box.createHorizontalBox(); //水平箱2
        JLabel label2 = new JLabel("パスワード");
        JPasswordField password = new JPasswordField(10);
        Component hStrut2 = Box.createHorizontalStrut(25);
        hbox2.add(label2);
        hbox2.add(hStrut2);
        hbox2.add(password);

        Box hbox3 = Box.createHorizontalBox(); //水平箱3
        JButton button1 = new JButton("ログイン");
        hbox3.add(button1);

        Box vbox = Box.createVerticalBox(); //创建一个垂直箱
        Component vStrut1 = Box.createVerticalStrut(10);
        Component vStrut2 = Box.createVerticalStrut(30);
        Component vStrut3 = Box.createVerticalStrut(30);
        vbox.add(vStrut3);
        vbox.add(hbox1);   //垂直箱加入一个水平箱1
        vbox.add(vStrut1);
        vbox.add(hbox2);  //垂直箱加入一个水平箱2
        vbox.add(vStrut2);
        vbox.add(hbox3);  //垂直箱加入一个水平箱3

        jp.add(vbox);
        jf.add(jp);
        jf.setVisible(true);  // 显示窗口


    }
}
