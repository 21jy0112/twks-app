package com.kh.twksproject.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import com.kh.twksproject.model.TwksFileAuthUtility;

public class LoginFrame implements ActionListener {
    private final JFrame loginFrame = new JFrame("TWKSアプリケーション");
    private final JLabel titleLabel = new JLabel("TWKS");
    private final JLabel mailLabel = new JLabel("メールアドレス");
    private final JTextField mailAddress = new JTextField(15);
    private final JLabel passwdLabel = new JLabel("パスワード");
    private final JPasswordField password = new JPasswordField(15);
    private final JLabel errorLabel = new JLabel("");
    private final JButton loginBtn = new JButton("ログイン");

    private static final int WIDTH = 400;
    private static final int HEIGHT = 250;

    public LoginFrame() {
        loginFrame.setSize(WIDTH, HEIGHT);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        init();
        loginFrame.setVisible(true);
    }

    public void init() {
        JPanel loginPanel = new JPanel();

        Box titleBox = Box.createHorizontalBox();
        Font titleFont = new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 24);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.gray);
        titleLabel.setPreferredSize(new Dimension(100, 50));
        titleBox.add(titleLabel);

        Box mailBox = Box.createHorizontalBox();
        mailLabel.setPreferredSize(new Dimension(100, 20));
        mailBox.add(mailLabel);
        mailAddress.setPreferredSize(new Dimension(150, 20));
        mailBox.add(mailAddress);

        Box pwBox = Box.createHorizontalBox();
        passwdLabel.setPreferredSize(new Dimension(100, 20));
        pwBox.add(passwdLabel);
        password.setPreferredSize(new Dimension(150, 20));
        pwBox.add(password);

        Box errorBox = Box.createHorizontalBox();
        errorLabel.setPreferredSize(new Dimension(300, 50));
        errorBox.add(errorLabel);

        Box btnBox = Box.createHorizontalBox();
        btnBox.add(loginBtn);

        Box loginBox = Box.createVerticalBox();
        Component vStrut1 = Box.createVerticalStrut(20);

        loginBox.add(titleBox);
        loginBox.add(mailBox);
        loginBox.add(vStrut1);
        loginBox.add(pwBox);
        loginBox.add(errorBox);
        loginBox.add(btnBox);

        loginPanel.add(loginBox);
        loginFrame.add(loginPanel);

        loginBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //boolean isCredentialValid = TwksUtility.checkCredentials(mailAddress.getText(), String.valueOf(password.getPassword()));
        boolean isCredentialValid = false;
        String usermail = mailAddress.getText();

        try {
            isCredentialValid = TwksFileAuthUtility.validateEmailPassword(mailAddress.getText(), String.valueOf(password.getPassword()));
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        if (isCredentialValid) {
            TwksFileAuthUtility.searchNameByEmail(usermail);
            loginFrame.dispose();
            new MainFrame();
        } else {
            Border redLine = BorderFactory.createLineBorder(Color.red);
            mailAddress.setBorder(redLine);
            password.setBorder(redLine);
            errorLabel.setText("メールアドレスまたはパスワードが正しくありません。");
            errorLabel.setForeground(Color.red);
        }
    }
}
