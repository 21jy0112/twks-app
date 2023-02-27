package com.kh.twksproject.view;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import com.kh.twksproject.model.TwksFileAuthUtility;
import com.kh.twksproject.model.TwksUtility;

public class PresenceFrame implements ActionListener {
    private final String username;

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

    private TrayIcon trayIcon = null;
    private SystemTray tray = null;

    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
    Date presenceTime = new Date();
    Date restTime = null;
    Date leavingTime = null;

    long workHours = 0;
    long workMinutes = 0;

    public PresenceFrame() {
        this.username = TwksFileAuthUtility.getUsername();

        presenceFrame.setSize(WIDTH, HEIGHT);
        presenceFrame.setLocationRelativeTo(null);
        presenceFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        timeLabel.setText("出勤時刻　" + formatTime.format(presenceTime));
        init();
        trayAction();
        presenceFrame.setVisible(true);
        doFolder();
        doSshot();
        doRecording();
    }

    public PresenceFrame(long workHours, long workMinutes) {
        this.username = TwksFileAuthUtility.getUsername();

        this.workHours = workHours;
        this.workMinutes = workMinutes;
        presenceFrame.setSize(WIDTH, HEIGHT);
        presenceFrame.setLocationRelativeTo(null);
        presenceFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        timeLabel.setText("　");
        init();
        trayAction();
        presenceFrame.setVisible(true);
        doFolder();
        doSshot();
        //doRecording();
    }

    void trayAction() {
        presenceFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    tray.add(trayIcon);
                } catch (AWTException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
                try {
                    tray.add(trayIcon);
                    presenceFrame.dispose();
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }
            }
        });
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

        timeBox.add(timeLabel);

        Box welcomeBox = Box.createHorizontalBox();
        nameLabel.setText(username);
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
        if (e.getSource() == restBtn) {
            doRecordStartTime();
            //stopRecording();
            stopSshot();
            restTime = new Date();
            long diff = restTime.getTime() - presenceTime.getTime();
            long hours = diff / (1000 * 60 * 60);
            long minutes = (diff - hours * (1000 * 60 * 60)) / (1000 * 60);
            presenceFrame.dispose();
            new RestFrame(hours, minutes);
        }
        if (e.getSource() == leavingBtn) {
            leavingTime = new Date();
            long diff = leavingTime.getTime() - presenceTime.getTime();
            long hours = diff / (1000 * 60 * 60);
            long minutes = (diff - hours * (1000 * 60 * 60)) / (1000 * 60);
            hours = hours + workHours;
            minutes = minutes + workMinutes;
            if (minutes >= 60) {
                minutes = minutes - 60;
                hours = hours + 1;
            }

            Object[] options = {"はい", "いいえ"};
            int option = JOptionPane.showOptionDialog(null,
                    "退勤時刻:　" + formatTime.format(leavingTime) + "\n今日実働時間:　" + hours + "時間" + minutes + "分\n退勤の打刻はよろしいでしょうか",
                    "確認通知", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (option == JOptionPane.YES_OPTION) {
                stopRecording();
                stopSshot();
                presenceFrame.dispose();
                new LeavingFrame();
                doZip();
                doDelete();
                try {
                    doUpLoad();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        }
        if (e.getSource() == minimizeBtn) {
            presenceFrame.setExtendedState(JFrame.ICONIFIED);
        }
    }



    void tray() {
        tray = SystemTray.getSystemTray();
        PopupMenu pop = new PopupMenu();
        MenuItem show = new MenuItem("開く");

        pop.add(show);

        Image image = Toolkit.getDefaultToolkit().getImage("src/com/kh/twksproject/model/spe06.png");
        trayIcon = new TrayIcon(image, "twks", pop);
        trayIcon.setImageAutoSize(true);

        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    tray.remove(trayIcon);
                    presenceFrame.setExtendedState(JFrame.NORMAL);
                    presenceFrame.setVisible(true); // 显示窗口
                    presenceFrame.toFront();
                }
            }
        });

        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                presenceFrame.setExtendedState(JFrame.NORMAL);
                presenceFrame.setVisible(true);
                presenceFrame.toFront();
            }
        });
    }

    void doSshot() {
        TwksUtility.autoScreenshot();
    }

    void doFolder() {
        TwksUtility.createFolder();
    }

    void stopSshot() {
        TwksUtility.stopScreenshot();
    }

    void doZip() {
        TwksUtility.zipFiles();
    }

    void doDelete() {
        SimpleDateFormat sshotDayFormat = new SimpleDateFormat("yyyyMMdd");
        String sshotDay = sshotDayFormat.format(new Date());
        String filePath = "src/demo/screenshotPack/" +TwksFileAuthUtility.getEmpId()+"_" + sshotDay;

        File folder = new File(filePath);

        if (folder.exists()) {
            TwksUtility.deleteFolder(folder);
        }
    }

    void doRecording() {
        TwksUtility.startRecord();
    }

    void stopRecording() {
        TwksUtility.stopRecord();
    }

    void doRecordStartTime(){
        SimpleDateFormat startTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String startTime = startTimeFormat.format(new Date());
        TwksFileAuthUtility.setStartTime(startTime);
    }

    private void doUpLoad() throws IOException {
        TwksFileAuthUtility.uploadFilesToServlet();
        System.out.println(TwksFileAuthUtility.getEmpId()+" "+TwksFileAuthUtility.getUsername());
    }
}
