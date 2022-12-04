package com.kh.twksproject.view;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.swing.*;
import javax.swing.border.Border;

public class PresenceFrame implements ActionListener {

    private final JFrame presenceFrame = new JFrame("TWKSアプリケーション");
    private final JLabel stateLabel = new JLabel("出勤中");
    private final JLabel timeLabel = new JLabel();
    private final JLabel nameLabel = new JLabel();
    private final JLabel welcomeLabel = new JLabel("さん、今日も頑張ってください。");
    private final JButton restBtn = new JButton("休憩");
    private final JButton leavingBtn = new JButton("退勤");
    private final JButton minimizeBtn = new JButton("非表示");

    private static final int WIDTH = 400;
    private static final int HEIGHT = 250;

    private TrayIcon trayIcon = null; // 托盘图标
    private SystemTray tray = null;

    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
    Date presenceTime = null;
    Date restTime = null;
    Date leavingTime = null;

    public PresenceFrame() {
        presenceFrame.setSize(WIDTH, HEIGHT);

        presenceFrame.setLocationRelativeTo(null);
        presenceFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        init();
        presenceFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                    try {
                        tray.add(trayIcon);
                    } catch (AWTException e1) {
                        e1.printStackTrace();
                    }
                //presenceFrame.setVisible(false);
            }

            @Override
            public void windowIconified(WindowEvent e) {
                try {
                    tray.add(trayIcon); // 将托盘图标添加到系统的托盘实例中
                    presenceFrame.dispose();
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }
            }
        });
        presenceFrame.setVisible(true);
        if (SystemTray.isSupported()) {
            this.tray();
        }
    }

    public void init() {
        JPanel presencePanel = new JPanel();
        Box stateBox = Box.createHorizontalBox();
        Component hStrut1 = Box.createHorizontalStrut(300);
        Border yellowLine = BorderFactory.createLineBorder(Color.yellow);
        stateLabel.setBorder(yellowLine);
        stateBox.add(hStrut1);
        stateBox.add(stateLabel);

        Box timeBox = Box.createHorizontalBox();
        presenceTime = new Date();
        timeLabel.setText("出勤時刻　"+formatTime.format(presenceTime));
        timeBox.add(timeLabel);
        
        Box welcomeBox = Box.createHorizontalBox();
        nameLabel.setText("〇〇");
        welcomeBox.add(nameLabel);
        welcomeBox.add(welcomeLabel);
        
        Box actionBtnBox = Box.createHorizontalBox();
        Component hStrut2 = Box.createHorizontalStrut(50);
        actionBtnBox.add(restBtn);
        actionBtnBox.add(hStrut2);
        actionBtnBox.add(leavingBtn);
        
        Box minimizeBtnBox = Box.createHorizontalBox();
        Component hStrut3 = Box.createHorizontalStrut(300);
        minimizeBtnBox.add(hStrut3);
        minimizeBtnBox.add(minimizeBtn);

        Box presenceBox = Box.createVerticalBox();
        Component vStrut1 = Box.createVerticalStrut(20);
        Component vStrut2 = Box.createVerticalStrut(25);
        Component vStrut3 = Box.createVerticalStrut(25);
        Component vStrut4 = Box.createVerticalStrut(20);
        presenceBox.add(stateBox);
        presenceBox.add(vStrut1);
        presenceBox.add(timeBox);
        presenceBox.add(vStrut2);
        presenceBox.add(welcomeBox);
        presenceBox.add(vStrut3);
        presenceBox.add(actionBtnBox);
        presenceBox.add(vStrut4);
        presenceBox.add(minimizeBtnBox);

        presencePanel.add(presenceBox);
        presenceFrame.add(presencePanel);

        restBtn.addActionListener(this);
        leavingBtn.addActionListener(this);
        minimizeBtn.addActionListener(this);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnStr = e.getActionCommand();
        if ("休憩".equals(btnStr)) {
            restTime = new Date();
            long diff = restTime.getTime()-presenceTime.getTime();
            long hours = diff/(1000*60*60);
            long minutes = (diff-hours*(1000*60*60))/(1000*60);
            presenceFrame.dispose();
            new RestFrame(hours,minutes);
        }
        if ("退勤".equals(btnStr)) {
            leavingTime = new Date();
            long diff = leavingTime.getTime()-presenceTime.getTime();
            long hours = diff/(1000*60*60);
            long minutes = (diff-hours*(1000*60*60))/(1000*60);
            Object[] options ={ "はい", "いいえ" };
            int option = JOptionPane.showOptionDialog(null,
                    "退勤時刻:　"+formatTime.format(leavingTime)+"\n今日実働時間:　"+hours+"時間"+minutes+"分\n退勤の打刻はよろしいでしょうか",
                    "確認通知",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (option==JOptionPane.YES_OPTION){
                presenceFrame.dispose();
                new LeavingFrame();
            }

        }
        if ("非表示".equals(btnStr)) {
            presenceFrame.setExtendedState(JFrame.ICONIFIED);
        }
    }

    void tray() {
        tray = SystemTray.getSystemTray(); // 获得本操作系统托盘的实例
        PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单
        MenuItem show = new MenuItem("show");
        MenuItem exit = new MenuItem("exit");
        pop.add(show);
        pop.add(exit);
        Image image = Toolkit.getDefaultToolkit().getImage("../model/spe06.png");
        trayIcon = new TrayIcon(image, "twks", pop);
        trayIcon.setImageAutoSize(true);
        /**
         * 添加鼠标监听器，当鼠标在托盘图标上双击时，默认显示窗口
         */
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // 鼠标双击
                    tray.remove(trayIcon); // 从系统的托盘实例中移除托盘图标
                    presenceFrame.setExtendedState(JFrame.NORMAL);
                    presenceFrame.setVisible(true); // 显示窗口
                    presenceFrame.toFront();
                }
            }
        });

        show.addActionListener(new ActionListener() { // 点击“显示窗口”菜单后将窗口显示出来
            @Override
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon); // 从系统的托盘实例中移除托盘图标
                presenceFrame.setExtendedState(JFrame.NORMAL);
                presenceFrame.setVisible(true); // 显示窗口
                presenceFrame.toFront();
            }
        });

        exit.addActionListener(new ActionListener() { // 点击“退出演示”菜单后退出程序
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // 退出程序
            }
        });


    }


}
