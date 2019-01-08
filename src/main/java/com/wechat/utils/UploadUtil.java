package com.wechat.utils;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class UploadUtil {

    private static final String UPLOAD_URL =
            "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    public static JSONObject upload(File file, String accessToken, String type) {

        DataInputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedReader reader = null;
        String result = null;

        try {

            if (!file.exists() || !file.isFile()) {
                throw new IOException("文件不存在！");
            }

            String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

            URL urlObject = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            //设置请求头
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");

            //设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");

            byte[] head = sb.toString().getBytes("utf-8");
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.write(head);

            inputStream = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bout = new byte[1024];
            while ((bytes = inputStream.read(bout)) != -1) {
                outputStream.write(bout, 0, bytes);
            }


            //结尾部分 定义最后数据分隔线
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");

            outputStream.write(foot);
            outputStream.flush();


            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {

                    inputStream.close();
                }

                if (outputStream != null) {

                    outputStream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        JSONObject jsonObject = JSONObject.fromObject(result);
        return jsonObject;
    }
}
