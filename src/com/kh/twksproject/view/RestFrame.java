package com.kh.twksproject.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.Border;

public class RestFrame extends JPanel implements ActionListener {

    private final JFrame restFrame = new JFrame("TWKSアプリケーション");
    private final JLabel stateLabel = new JLabel("休憩中");
    private final JLabel nameLabel = new JLabel();
    private final JLabel welcomeLabel = new JLabel("さん、お疲れ様です。");
    private final JLabel restLabel = new JLabel("休憩中です！");
    private final JLabel workTimeLabel = new JLabel();
    private final JLabel restTimeLabel = new JLabel();
    private final JButton restEndBtn = new JButton("休憩終了");

    private static final int WIDTH = 400;
    private static final int HEIGHT = 250;

    private long countMis, countSec, countMin, countHour;
    private DecimalFormat textFormat = new DecimalFormat("00");
    Timer timer = new Timer(10, this);


    public RestFrame(long hours, long minutes) {
        timer.start();
        restFrame.setSize(WIDTH, HEIGHT);
        restFrame.setLocationRelativeTo(null);
        restFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        workTimeLabel.setText("今日実働時間:　" + hours + "時間" + minutes + "分");
        init();
        restFrame.setVisible(true);
    }


    public void init() {
        JPanel restPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                restTimeLabel.setText("今までの休憩時間:　"+textFormat.format(countHour) + "時間" + textFormat.format(countMin) +
                        "分" + textFormat.format(countSec) + "秒");
                repaint();
            }};
        Box stateBox = Box.createHorizontalBox();
        Component hStrut1 = Box.createHorizontalStrut(300);
        Border blueLine = BorderFactory.createLineBorder(Color.blue);
        stateLabel.setBorder(blueLine);
        stateBox.add(hStrut1);
        stateBox.add(stateLabel);

        Box welcomeBox = Box.createHorizontalBox();
        nameLabel.setText("〇〇");
        welcomeBox.add(nameLabel);
        welcomeBox.add(welcomeLabel);

        Box restLabelBox = Box.createHorizontalBox();
        restLabelBox.add(restLabel);

        Box workTimeBox = Box.createHorizontalBox();
        //workTimeLabel.setText("今日実働時間:　2時間30分");
        workTimeBox.add(workTimeLabel);

        Box restTimeBox = Box.createHorizontalBox();
        //restTimeLabel.setText("今までの休憩時間：1時05分");
        restTimeBox.add(restTimeLabel);

        Box restEndBtnBox = Box.createHorizontalBox();
        restEndBtnBox.add(restEndBtn);

        Box restBox = Box.createVerticalBox();
        Component vStrut1 = Box.createVerticalStrut(20);
        Component vStrut2 = Box.createVerticalStrut(25);
        Component vStrut3 = Box.createVerticalStrut(25);
        restBox.add(stateBox);
        restBox.add(vStrut1);
        restBox.add(welcomeBox);
        restBox.add(restLabelBox);
        restBox.add(vStrut2);
        restBox.add(workTimeBox);
        restBox.add(restTimeBox);
        restBox.add(vStrut3);
        restBox.add(restEndBtnBox);

        restPanel.add(restBox);
        restFrame.add(restPanel);

        restEndBtn.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==restEndBtn){
            restFrame.dispose();
            new PresenceFrame();
        }else {
            countMis++;
            if (countMis >= 99) {
                countSec++;
                countMis = 0;
                if (countSec >= 59) {
                    countMin++;
                    countSec = 0;
                    if (countMin >= 59) {
                        countHour++;
                        countMin = 0;
                    }
                }
            }
        }
    }
}
