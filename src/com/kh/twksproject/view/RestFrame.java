package com.kh.twksproject.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestFrame  implements ActionListener {
    private final JFrame restFrame = new JFrame("TWKSアプリケーション");

    private static final int WIDTH = 400;
    private static final int HEIGHT = 250;

    public RestFrame() {
        restFrame.setSize(WIDTH, HEIGHT);
        restFrame.setLocationRelativeTo(null);
        restFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        init();
        restFrame.setVisible(true);
    }
    public void init() {

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
