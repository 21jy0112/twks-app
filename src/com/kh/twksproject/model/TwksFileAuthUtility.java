package com.kh.twksproject.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TwksFileAuthUtility {
    private static String username;

    public static boolean validateEmailPassword(String email, String password) throws MalformedURLException {
        int responseCode = 0;
        String url = "***************************************";
        URL obj = null;
        try {
            obj = new URL(url);
            HttpURLConnection con = null;
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            // 设置请求头
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // 设置请求体
            String urlParameters = "email=" + email + "&password=" + password+"&action=validate_password";
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            // 获取响应代码
            responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (responseCode == 200) {
            return true;
        } else if (responseCode == 401) {
            return false;
        } else {
            return false;
        }

    }

    public static void searchNameByEmail(String email){
        String name = null;
        String url = "***************************************";
        URL obj = null;
        try {
            obj = new URL(url);
            HttpURLConnection con = null;
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            // 设置请求头
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // 设置请求体
            String urlParameters = "email=" + email +"&action=get_username";
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con.setRequestProperty("Content-Length", String.valueOf(postData.length));

            // 发送POST请求
            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            // 处理响应
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String line;
                StringBuilder content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                }
                username = content.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUsername(){
        return username;
    }
}
