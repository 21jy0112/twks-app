package com.kh.twksproject.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainFrame implements ActionListener {
    private final JFrame mainFrame = new JFrame("TWKSアプリケーション");
    private final JLabel stateLabel = new JLabel("未出勤");
    private final JLabel nameLabel = new JLabel();
    private final JLabel welcomeLabel = new JLabel("さん　ようこそ");
    private final JButton presenceBtn = new JButton("出勤");

    private static final int WIDTH = 400;
    private static final int HEIGHT = 250;

    ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

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

        Runnable presenceNoticeTask = new Runnable() {
            // run 方法内的内容就是定时任务的内容
            @Override
            public void run() {
                Object[] options ={ "はい"};
                JOptionPane.showOptionDialog(null,
                        "ログイン以降「出勤」の打刻がありません。\n「出勤」処理を行ってよろしいでしょうか？\n",
                        "確認通知",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            }
        };

        // 参数解释
        // 1=此次任务、2=任务开始延迟时间、3=任务之间间隔时间、4=单位
        service.scheduleWithFixedDelay(presenceNoticeTask, 5, 5, TimeUnit.MINUTES);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        service.shutdown();
        mainFrame.dispose();
        new PresenceFrame();
    }
}
