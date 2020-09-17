package com.project.meetinglive.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http请求工具
 * @author hejinguo
 * @version $Id: HttpHelp.java, v 0.1 2019年11月17日 下午4:42:10
 */
public final class HttpHelp {
    private static final Logger logger            = LoggerFactory.getLogger(HttpHelp.class);

    public static final String  CONTENT_TYPE_XML  = "application/soap+xml;charset=UTF-8";

    public static final String  CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    private static final String UTF8              = "UTF-8";

    /**
     * @param postUrl
     * @param postData
     * @param contentType
     * @return
     * 发送post请求
     */
    public static final String post(String postUrl, String postData, String contentType) {
        OutputStream out = null;
        InputStream in = null;
        try {
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置http请求协议头
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("Content-Length", "" + postData.length());
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            out = conn.getOutputStream();
            out.write(postData.getBytes(UTF8));
            out.flush();
            // 获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "";
            }
            // 获取响应内容
            String line = null;
            StringBuilder appender = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                appender.append(line);
            }
            return appender.toString();
        } catch (ConnectException e) {
            if (logger.isErrorEnabled()) {
                logger.error("TECH-TRANS接口连接被关闭或通行协议错误!", e);
            }
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("TECH-TRANS接口连接IO异常!", e);
            }
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    if (logger.isErrorEnabled()) {
                        logger.error(e.getMessage());
                    }
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    if (logger.isErrorEnabled()) {
                        logger.error(e.getMessage());
                    }
                }
            }
        }
        return "";
    }

}
