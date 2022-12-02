package com.kh.twksproject.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
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

    public PresenceFrame() {
        presenceFrame.setSize(WIDTH, HEIGHT);

        presenceFrame.setLocationRelativeTo(null);
        presenceFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        init();
        presenceFrame.setVisible(true);
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
        timeLabel.setText("出勤時刻：9:00");
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
