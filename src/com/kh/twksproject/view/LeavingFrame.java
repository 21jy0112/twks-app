package com.kh.twksproject.view;

import com.kh.twksproject.model.TwksFileAuthUtility;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class LeavingFrame implements ActionListener {
    private final String username;
    private final JFrame leavingFrame = new JFrame("TWKSアプリケーション");
    private final JLabel leavingLabel = new JLabel("退勤しました。");
    private final JLabel nameLabel = new JLabel();
    private final JLabel welcomeLabel = new JLabel("さん、今日はお疲れ様でした。");
    private final JButton endBtn = new JButton("終わり");

    private static final int WIDTH = 400;
    private static final int HEIGHT = 250;

    public LeavingFrame() {
        this.username = TwksFileAuthUtility.getUsername();

        leavingFrame.setSize(WIDTH, HEIGHT);
        leavingFrame.setLocationRelativeTo(null);
        leavingFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        init();
        leavingFrame.setVisible(true);
    }

    public void init() {
        JPanel leavingPanel = new JPanel();
        Box leavingLabelBox = Box.createHorizontalBox();
        leavingLabelBox.add(leavingLabel);

        Box welcomeBox = Box.createHorizontalBox();
        nameLabel.setText(username);
        welcomeBox.add(nameLabel);
        welcomeBox.add(welcomeLabel);

        Box endBtnBox = Box.createHorizontalBox();
        endBtnBox.add(endBtn);

        Box leavingBox = Box.createVerticalBox();
        Component vStrut1 = Box.createVerticalStrut(35);
        Component vStrut2 = Box.createVerticalStrut(25);
        Component vStrut3 = Box.createVerticalStrut(40);
        leavingBox.add(vStrut1);
        leavingBox.add(leavingLabelBox);
        leavingBox.add(vStrut2);
        leavingBox.add(welcomeBox);
        leavingBox.add(vStrut3);
        leavingBox.add(endBtnBox);

        leavingPanel.add(leavingBox);
        leavingFrame.add(leavingPanel);

        endBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        leavingFrame.dispatchEvent(new WindowEvent(leavingFrame, WindowEvent.WINDOW_CLOSING));
    }
}
