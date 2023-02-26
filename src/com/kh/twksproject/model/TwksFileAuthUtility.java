package com.kh.twksproject.model;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TwksFileAuthUtility {
    private static String username = null;
    private static String empId = null;
    private static String startTime = null;
    private static String endTime = null;

    private static final int BUFFER_SIZE = 4096;

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

    public static String getStartTime() {
        return startTime;
    }

    public static void setStartTime(String startTime) {
        TwksFileAuthUtility.startTime = startTime;
    }

    public static String getEndTime() {
        return endTime;
    }

    public static void setEndTime(String endTime) {
        TwksFileAuthUtility.endTime = endTime;
    }

    public static void uploadFilesToServlet() throws IOException  {
        SimpleDateFormat sshotDayFormat = new SimpleDateFormat("yyyyMMdd");
        String day = sshotDayFormat.format(new Date());

        String url = "http://localhost:8080/twks-backend/TesePass";
        String action = "upload_file_screenshot";
        String motionsPackPath = "src/demo/motionsPack/"+getEmpId()+"_MotionData_"+day+".txt";
        String screenshotPackPath = "src/demo/screenshotPack/"+getEmpId()+"_"+day+".zip";

        String boundary = UUID.randomUUID().toString();
        String lineEnding = "\r\n";

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        OutputStream out = conn.getOutputStream();

        // 添加action参数
        out.write(("--" + boundary + lineEnding).getBytes());
        out.write(("Content-Disposition: form-data; name=\"action\"" + lineEnding).getBytes());
        out.write((lineEnding).getBytes());
        out.write((action + lineEnding).getBytes());

        // 添加motionsPack文件
        File motionsPack = new File(motionsPackPath);
        out.write(("--" + boundary + lineEnding).getBytes());
        out.write(("Content-Disposition: form-data; name=\"motionsPack\"; filename=\"" + motionsPack.getName() + "\"" + lineEnding).getBytes());
        out.write(("Content-Type: text/plain" + lineEnding).getBytes());
        out.write((lineEnding).getBytes());

        FileInputStream motionsPackInputStream = new FileInputStream(motionsPack);
        byte[] motionsPackBuffer = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = motionsPackInputStream.read(motionsPackBuffer)) != -1) {
            out.write(motionsPackBuffer, 0, bytesRead);
        }
        motionsPackInputStream.close();

        // 添加screenshotPack文件
        File screenshotPack = new File(screenshotPackPath);
        out.write((lineEnding).getBytes());
        out.write(("--" + boundary + lineEnding).getBytes());
        out.write(("Content-Disposition: form-data; name=\"screenshotPack\"; filename=\"" + screenshotPack.getName() + "\"" + lineEnding).getBytes());
        out.write(("Content-Type: application/zip" + lineEnding).getBytes());
        out.write((lineEnding).getBytes());

        FileInputStream screenshotPackInputStream = new FileInputStream(screenshotPack);
        byte[] screenshotPackBuffer = new byte[BUFFER_SIZE];
        while ((bytesRead = screenshotPackInputStream.read(screenshotPackBuffer)) != -1) {
            out.write(screenshotPackBuffer, 0, bytesRead);
        }
        screenshotPackInputStream.close();

        // 结束上传
        out.write((lineEnding).getBytes());
        out.write(("--" + boundary + "--" + lineEnding).getBytes());

        // 获取响应结果
        InputStream in = new BufferedInputStream(conn.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();

        out.close();
        conn.disconnect();
    }

    public static void uploadTimeToServlet(){
        String url = "http://localhost:8080/twks-backend/TesePass";
        URL obj = null;
        try {
            obj = new URL(url);
            HttpURLConnection con = null;
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String action = "upload_time";
            String postData = "empId=" + empId + "&startTime=" + startTime + "&endTime=" + endTime + "&action=" + action;

            OutputStream os = con.getOutputStream();
            os.write(postData.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();
                System.out.println(responseCode);

            } else {
                System.out.println("HTTP POST请求失败，响应码为 " + responseCode);
            }
            con.disconnect();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}