package com.kh.twksproject.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class PresenceFrame implements ActionListener {
    private static final int NORMAL = 0;

    private final JFrame presenceFrame = new JFrame("TWKSアプリケーション");
    private final JButton minimizeBtn = new JButton("非表示");

    private TrayIcon trayIcon;
    private SystemTray systemTray;

    private static final int WIDTH = 400;
    private static final int HEIGHT = 250;

    public PresenceFrame() {
        presenceFrame.setSize(WIDTH, HEIGHT);

        URL imageurl = PresenceFrame.class.getClassLoader().getResource("spe06.png");
        Image image = new ImageIcon(imageurl).getImage();
        presenceFrame.setIconImage(image);

        presenceFrame.setLocationRelativeTo(null);
        presenceFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        init();
        presenceFrame.setVisible(true);

        trayIcon = new TrayIcon(image);
        systemTray = SystemTray.getSystemTray();

        trayIcon.setImageAutoSize(true);
        presenceFrame.addWindowListener(new WindowAdapter() {
            // 			窗口最小化事件
            @Override
            public void windowIconified(WindowEvent e) {
                try {
//					窗口最小化时显示托盘图标
                    systemTray.add(trayIcon);
                } catch (AWTException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
//				设置窗口不可见
                presenceFrame.setVisible(false);
            }
        });

//		鼠标监听
        trayIcon.addMouseListener(new MouseAdapter() {
            //			鼠标点击事件
            @Override
            public void mouseClicked(MouseEvent e) {
//				鼠标点击次数
                int clt = e.getClickCount();
                if (clt == 1) {
//					鼠标点击托盘图标一次，恢复原窗口
                    presenceFrame.setExtendedState(NORMAL);
                }
//				托盘图标消失
                systemTray.remove(trayIcon);
//				设置窗口可见
                presenceFrame.setVisible(true);
            }
        });


    }

    public void init() {
        JPanel presencePanel = new JPanel();

        Box minimizeBtnBox = Box.createHorizontalBox();
        minimizeBtnBox.add(minimizeBtn);

        Box presenceBox = Box.createVerticalBox();
        presenceBox.add(minimizeBtnBox);

        presencePanel.add(presenceBox);
        presenceFrame.add(presencePanel);

        minimizeBtn.addActionListener(this);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnStr = e.getActionCommand();
        if ("休憩".equals(btnStr)) {
            presenceFrame.dispose();
            new RestFrame();
        }
        if ("退勤".equals(btnStr)) {
            presenceFrame.dispose();
            new LeavingFrame();
        }
        if ("非表示".equals(btnStr)) {
            presenceFrame.setExtendedState(JFrame.ICONIFIED);
        }
    }
}
