package com.kh.twksproject.model;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TwksFileAuthUtility {
    private static String username = null;
    private static String empId = null;

    private static final String BOUNDARY = "------------------------" + Long.toHexString(System.currentTimeMillis());

    public static boolean validateEmailPassword(String email, String password) throws MalformedURLException {
        int responseCode = 0;
        String url = "http://localhost:8080/twks-backend/TesePass";
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
            String urlParameters = "email=" + email + "&password=" + password + "&action=validate_password";
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

    public static void searchNameByEmail(String email) {
        String name = null;
        String url = "http://localhost:8080/twks-backend/TesePass";
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
            String urlParameters = "email=" + email + "&action=get_username_empId";
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
                String[] result = content.toString().split(",");
                if (result.length == 2) {
                    username = result[0];
                    System.out.println(result[0]);
                    empId = result[1];
                    System.out.println(result[1]);
                    // ...
                } else {
                    System.out.println(result[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUsername() {
        return username;
    }

    public static String getEmpId() {
        return empId;
    }

    public static void uploadFilesToServlet() {
        String url = "http://localhost:8080/twks-backend/TesePass";
        String charset = "UTF-8";
        File motionFolder = new File("src/demo/motionsPack");
        File screenshotFolder = new File("src/demo/screenshotPack");

        String action = "upload_file_screenshot"; // 添加 action 参数

        URL uploadUrl = null;
        try {
            uploadUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) uploadUrl.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream outputStream = conn.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

            // 添加 action 参数
            writer.append("--" + BOUNDARY).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"action\"").append("\r\n");
            writer.append("\r\n");
            writer.append(action).append("\r\n");
            writer.flush();



            // 添加motion文件夹下的所有txt文件
            File[] motionFiles = motionFolder.listFiles();
            for (File motionFile : motionFiles) {
                if (motionFile.isFile() && motionFile.getName().endsWith(".txt")) {
                    String fileName = motionFile.getName();
                    writer.append("--" + BOUNDARY).append("\r\n");
                    writer.append("Content-Disposition: form-data; name=\"motion_file\"; filename=\"" + fileName + "\"").append("\r\n");
                    writer.append("Content-Type: text/plain; charset=" + charset).append("\r\n\r\n");
                    writer.flush();

                    FileInputStream inputStream = new FileInputStream(motionFile);
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();
                    inputStream.close();

                    writer.append("\r\n");
                    writer.flush();
                }
            }

            // 添加screenshot文件夹下的所有zip文件
            File[] screenshotFiles = screenshotFolder.listFiles();
            for (File screenshotFile : screenshotFiles) {
                if (screenshotFile.isFile() && screenshotFile.getName().endsWith(".zip")) {
                    String fileName = screenshotFile.getName();
                    writer.append("--" + BOUNDARY).append("\r\n");
                    writer.append("Content-Disposition: form-data; name=\"screenshot_file\"; filename=\"" + fileName + "\"").append("\r\n");
                    writer.append("Content-Type: application/zip; charset=" + charset).append("\r\n\r\n");
                    writer.flush();

                    FileInputStream inputStream = new FileInputStream(screenshotFile);
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();
                    inputStream.close();

                    writer.append("\r\n");
                    writer.flush();
                }
            }

            // 添加结束边界符
            writer.append("--" + BOUNDARY + "--").append("\r\n");
            writer.close();
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("upload_file_screenshot");
    }
}