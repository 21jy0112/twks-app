package com.kh.twksproject.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    Date presenceTime = new Date();
    Date restTime = null;
    Date leavingTime = null;

    long workHours = 0;
    long workMinutes = 0;

    ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    public PresenceFrame() {
        presenceFrame.setSize(WIDTH, HEIGHT);
        presenceFrame.setLocationRelativeTo(null);
        presenceFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        timeLabel.setText("出勤時刻　" + formatTime.format(presenceTime));
        init();
        trayAction();
        presenceFrame.setVisible(true);
        doSshot();

    }

    public PresenceFrame(long workHours, long workMinutes) {
        this.workHours = workHours;
        this.workMinutes = workMinutes;
        presenceFrame.setSize(WIDTH, HEIGHT);
        presenceFrame.setLocationRelativeTo(null);
        presenceFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        timeLabel.setText("　");
        init();
        trayAction();
        presenceFrame.setVisible(true);
        doSshot();
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
            service.shutdown();
            restTime = new Date();
            long diff = restTime.getTime() - presenceTime.getTime();
            long hours = diff / (1000 * 60 * 60);
            long minutes = (diff - hours * (1000 * 60 * 60)) / (1000 * 60);
            presenceFrame.dispose();
            new RestFrame(hours, minutes);
        }
        if ("退勤".equals(btnStr)) {
            service.shutdown();
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
        MenuItem show = new MenuItem("開く");

        pop.add(show);

        Image image = Toolkit.getDefaultToolkit().getImage("src/com/kh/twksproject/model/spe06.png");
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
    }

    void doSshot() {

        Runnable presenceNoticeTask = new Runnable() {
            // run 方法内的内容就是定时任务的内容
            @Override
            public void run() {
                try {
                    //获取屏幕分辨率
                    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                    //创建该分辨率的矩形对象
                    Rectangle screenRect = new Rectangle(d);
                    Robot robot = new Robot();


                    BufferedImage bufferedImage = robot.createScreenCapture(screenRect);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String sshotName = sdf.format(new Date());
                    //保存截图
                    File file = new File("src/demo/screenshotPack/sshot_" + sshotName + ".png");

                    ImageIO.write(bufferedImage, "png", file);

                    //根据这个矩形截图

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // 参数解释
        // 1=此次任务、2=任务开始延迟时间、3=任务之间间隔时间、4=单位
        service.scheduleWithFixedDelay(presenceNoticeTask, 5, 5, TimeUnit.SECONDS);

    }


}
