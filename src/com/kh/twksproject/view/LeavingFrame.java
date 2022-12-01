package com.kh.twksproject.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeavingFrame  implements ActionListener {
    private final JFrame leavingFrame = new JFrame("TWKSアプリケーション");

    private static final int WIDTH = 400;
    private static final int HEIGHT = 250;
    public LeavingFrame() {
        leavingFrame.setSize(WIDTH, HEIGHT);
        leavingFrame.setLocationRelativeTo(null);
        leavingFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        init();
        leavingFrame.setVisible(true);
    }
    public void init() {

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
