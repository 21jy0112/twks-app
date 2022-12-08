package com.kh.twksproject.view;

import com.kh.twksproject.model.TwksUtility;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ScheduledFuture;

public class MainFrame implements ActionListener {
    private final JFrame mainFrame = new JFrame("TWKSアプリケーション");
    private final JLabel stateLabel = new JLabel("未出勤");
    private final JLabel nameLabel = new JLabel();
    private final JLabel welcomeLabel = new JLabel("さん　ようこそ");
    private final JButton presenceBtn = new JButton("出勤");

    private static final int WIDTH = 400;
    private static final int HEIGHT = 250;

    public MainFrame() {
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        init();
        mainFrame.setVisible(true);
    }

    public void init() {
        JPanel mainPanel = new JPanel();
        Box stateBox = Box.createHorizontalBox();
        Component hStrut1 = Box.createHorizontalStrut(300);
        Border greenLine = BorderFactory.createLineBorder(Color.green);
        stateLabel.setBorder(greenLine);
        stateBox.add(hStrut1);
        stateBox.add(stateLabel);

        Box welcomeBox = Box.createHorizontalBox();
        nameLabel.setText("〇〇");
        nameLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        welcomeLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        welcomeBox.add(nameLabel);
        welcomeBox.add(welcomeLabel);

        Box btnBox = Box.createHorizontalBox();
        btnBox.add(presenceBtn);

        Box mainBox = Box.createVerticalBox();
        Component vStrut1 = Box.createVerticalStrut(50);
        Component vStrut2 = Box.createVerticalStrut(50);
        mainBox.add(stateBox);
        mainBox.add(vStrut1);
        mainBox.add(welcomeBox);
        mainBox.add(vStrut2);
        mainBox.add(btnBox);

        mainPanel.add(mainBox);
        mainFrame.add(mainPanel);

        presenceBtn.addActionListener(this);

        doNotification();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        stopNotification();
        mainFrame.dispose();
        new PresenceFrame();
    }

    void doNotification() {
        TwksUtility.autoNotification();
    }

    void stopNotification() {
        ScheduledFuture future = TwksUtility.getFuture();
        future.cancel(true);
    }
}
