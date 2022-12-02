package demo.uw05;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class uw05_02 {
    private static int WIDTH = 400;
    private static int HEIGHT = 200;

    public static void main(String[] args) {
        JFrame jf = new JFrame("TWKS");

        jf.setSize(WIDTH, HEIGHT);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jp = new JPanel();

        Box hbox1 = Box.createHorizontalBox(); // 水平箱1
        Component hStrut1 = Box.createHorizontalStrut(300);// 一个20单位不可见组件
        Border blackline = BorderFactory.createLineBorder(Color.yellow);
        JLabel label1 = new JLabel("出勤中");
        label1.setBorder(blackline);
        hbox1.add(hStrut1);
        hbox1.add(label1); // 水平箱加入一个标签

        Box hbox4 = Box.createHorizontalBox(); // 水平箱4
        JLabel label4 = new JLabel("〇〇さん、今日も頑張ってください。");
        hbox4.add(label4);

        Box hbox3 = Box.createHorizontalBox(); // 水平箱3
        JButton button1 = new JButton("休憩");
        Component hStrut3 = Box.createHorizontalStrut(50);
        JButton button2 = new JButton("退勤");
        hbox3.add(button1);
        hbox3.add(hStrut3);
        hbox3.add(button2);

        Box hbox5 = Box.createHorizontalBox(); // 水平箱3
        JButton button3 = new JButton("非表示");
        Component hStrut2 = Box.createHorizontalStrut(300);
        hbox5.add(hStrut2);
        hbox5.add(button3);

        Box vbox = Box.createVerticalBox(); // 创建一个垂直箱
        Component vStrut1 = Box.createVerticalStrut(20);
        Component vStrut2 = Box.createVerticalStrut(10);
        Component vStrut3 = Box.createVerticalStrut(10);
        Component vStrut4 = Box.createVerticalStrut(10);
        vbox.add(hbox1); // 垂直箱加入一个水平箱1
        vbox.add(vStrut1);
        vbox.add(hbox4); // 垂直箱加入一个水平箱2
        vbox.add(vStrut3);
        vbox.add(hbox3); // 垂直箱加入一个水平箱3
        vbox.add(vStrut4);
        vbox.add(hbox5);

        jp.add(vbox);
        jf.add(jp);
        jf.setVisible(true); // 显示窗口

        //JOptionPane.showConfirmDialog(null, "退勤時刻:　17:00\n今日実働時間:　7時間30分\n 退勤の打刻はよろしいでしょうか", "確認通知", JOptionPane.YES_NO_OPTION);
        Object[] options ={ "はい", "いいえ" };
        int m = JOptionPane.showOptionDialog(null, "退勤時刻:　17:00\n今日実働時間:　7時間30分\n 退勤の打刻はよろしいでしょうか", "確認通知",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }
}
