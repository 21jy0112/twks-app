package com.kh.twksproject.model;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TwksUtility {
    private static ScheduledFuture future;
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(2);

    public static void deleteFolder(File folder) {
        // 获取文件夹下的所有文件
        File[] files = folder.listFiles();

        // 如果文件夹不为空
        if (files != null) {
            // 遍历文件夹下的所有文件
            for (File file : files) {
                // 如果当前文件是文件夹
                if (file.isDirectory()) {
                    // 递归调用deleteFolder()方法，删除子文件夹
                    deleteFolder(file);
                } else {
                    // 如果当前文件是普通文件，直接删除
                    file.delete();
                }
            }
        }

        // 删除空文件夹
        folder.delete();
    }

    public static void createFolder() {
        SimpleDateFormat sshotDayFormat = new SimpleDateFormat("yyyyMMdd");
        String sshotDay = sshotDayFormat.format(new Date());
        String sshotFile = "sshot" + sshotDay;
        String saveDir = "src/demo/screenshotPack/" + sshotFile;
        Path path = Paths.get(saveDir);
        Path tempPath = Paths.get("src/demo/screenshotPack/temp");
        try {
            // 使用 Files.createDirectories 创建该文件夹
            Files.createDirectories(path);
            Files.createDirectories(tempPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void autoScreenshot() {
        future = SCHEDULER.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                takeScreenshot();
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    public static void takeScreenshot() {
        try {
            //获取屏幕分辨率
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            //创建该分辨率的矩形对象
            Rectangle screenRect = new Rectangle(d);
            Robot robot = new Robot();

            BufferedImage bufferedImage = robot.createScreenCapture(screenRect);

            SimpleDateFormat sshotDayFormat = new SimpleDateFormat("yyyyMMdd");
            String sshotDay = sshotDayFormat.format(new Date());
            String saveDir = "src/demo/screenshotPack/sshot" + sshotDay;

            SimpleDateFormat sshotTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String sshotTime = sshotTimeFormat.format(new Date());
            String sshotName = "/sshot_" + sshotTime;
            //保存截图
            File file = new File(saveDir + sshotName + ".png");

            ImageIO.write(bufferedImage, "png", file);

            //根据这个矩形截图

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void autoNotification() {
        future = SCHEDULER.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                showNotification();
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    public static void showNotification() {
        Object[] options = {"はい"};
        JOptionPane.showOptionDialog(null,
                "ログイン以降「出勤」の打刻がありません。\n「出勤」処理を行ってよろしいでしょうか？\n",
                "確認通知", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

    }

    public static ScheduledFuture getFuture() {
        return future;
    }

    public static void zipFiles() {

        SimpleDateFormat sshotDayFormat = new SimpleDateFormat("yyyyMMdd");
        String sshotDay = sshotDayFormat.format(new Date());
        String zipFolder = "src/demo/screenshotPack/sshot" + sshotDay;
        String zipFile = "src/demo/screenshotPack/temp/sshot" + sshotDay + ".zip";

        try {

            // 创建压缩文件的输出流
            OutputStream os = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(os);

            // 遍历要压缩的文件夹
            File dir = new File(zipFolder);
            for (File file : dir.listFiles()) {
                // 读取文件的输入流
                InputStream is = new FileInputStream(file);

                // 创建压缩条目，并添加到压缩文件中
                ZipEntry entry = new ZipEntry(file.getName());
                zos.putNextEntry(entry);

                // 读取文件的输入流，并写入压缩文件的输出流
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }

                // 关闭压缩条目，结束压缩
                zos.closeEntry();
                is.close();
            }

            // 关闭压缩文件的输出流，结束压缩
            zos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkCredentials(String username, String password) {
        return "123".equals(username) && "123".equals(password);
    }

}