package demo.uw02;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class uw02_S1_01 {
    private static int WIDTH = 400;
    private static int HEIGHT = 200;
    public static void main(String[] args) {
        JFrame jf = new JFrame("TWKS");
        jf.getContentPane().setVisible(false);

        jf.setSize(WIDTH, HEIGHT);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jp = new JPanel();

        Box hbox1 = Box.createHorizontalBox(); //水平箱1
        Component hStrut1 = Box.createHorizontalStrut(300);//一个20单位不可见组件
        Border blackline = BorderFactory.createLineBorder(Color.green);
        JLabel label1 = new JLabel("未出勤");
        label1.setBorder(blackline);
        hbox1.add(hStrut1);
        hbox1.add(label1); //水平箱加入一个标签


        Box hbox2 = Box.createHorizontalBox(); //水平箱2
        JLabel label2 = new JLabel("〇〇さん　ようこそ");
        label2.setFont(new Font (Font.DIALOG, Font.BOLD, 20));
        hbox2.add(label2);

        Box hbox3 = Box.createHorizontalBox(); //水平箱3
        JButton button1 = new JButton("出勤");
        hbox3.add(button1);

        Box vbox = Box.createVerticalBox(); //创建一个垂直箱
        Component vStrut1 = Box.createVerticalStrut(30);
        Component vStrut2 = Box.createVerticalStrut(30);
        vbox.add(hbox1);   //垂直箱加入一个水平箱1
        vbox.add(vStrut1);
        vbox.add(hbox2);  //垂直箱加入一个水平箱2
        vbox.add(vStrut2);
        vbox.add(hbox3);  //垂直箱加入一个水平箱3

        jp.add(vbox);
        jf.add(jp);
        jf.setVisible(true);  // 显示窗口

        Object[] options ={ "はい"};
        int m = JOptionPane.showOptionDialog(null, "ログイン以降「出勤」の打刻がありません。\n「出勤」処理を行ってよろしいでしょうか？\n", "確認通知",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);


    }
}
