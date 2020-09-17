package com.project.meetinglive.common.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestFileUploadUtil {
    private static final Logger logger    = LoggerFactory
                                              .getLogger(HttpRequestFileUploadUtil.class);

    static boolean              proxySet  = false;
    static String               proxyHost = "127.0.0.1";
    static int                  proxyPort = 8087;

    public static String doUploadFilePost(String url, File file, boolean isproxy) {
        OutputStream out = null;
        DataInputStream in = null;
        String result = "";
        // 定义数据分隔线
        final String BOUNDARY = "========7d4a6d158c9";
        final String newLine = "\r\n";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = null;
            if (isproxy) {//使用代理模式
                @SuppressWarnings("static-access")
                Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP, new InetSocketAddress(proxyHost,
                    proxyPort));
                conn = (HttpURLConnection) realUrl.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) realUrl.openConnection();
            }
            // 打开和URL之间的连接
            conn.setConnectTimeout(45000);
            conn.setReadTimeout(45000);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // POST方法     
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new DataOutputStream(conn.getOutputStream());
            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append(newLine);
            // 文件参数,photo参数名可以随意修改
            sb.append("Content-Disposition: form-data;name=\"imagefile[]\";filename=\""
                      + file.getName() + "\"" + "\r\n");
            sb.append("Content-Type:application/octet-stream"); // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);
            // 发送请求参数
            out.write(sb.toString().getBytes());
            // 数据输入流,用于读取文件数据
            in = new DataInputStream(new FileInputStream(file));
            byte[] bufferOut = new byte[1024 * 8];
            int bytes = 0;
            // 每次读8KB数据,并且将文件数据写入到输出流中 
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            // 最后添加换行 
            out.write(newLine.getBytes());
            in.close();

            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (newLine + "--" + BOUNDARY + "--" + newLine).getBytes();
            // 写上结尾标识 
            out.write(end_data);
            out.flush();
            out.close();

            // 定义BufferedReader输入流来读取URL的响应 
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result += line;
                //这里读取的是上边url对应的上传文件接口的返回值，读取出来后，然后接着返回到前端，实现接口中调用接口的方式 
            }
        } catch (ConnectException e) {
            if (logger.isErrorEnabled()) {
                logger.error("接口连接被关闭或通行协议错误!", e);
            }
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常！", e);
        } finally {//使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                logger.error("发送 POST 请求时关闭输入输出流时异常！", ex);
            }
        }
        return result;
    }

    public static String doUploadHtmlPost(String url, File file, boolean isproxy) {
        OutputStream out = null;
        DataInputStream in = null;
        String result = "";
        // 定义数据分隔线
        final String BOUNDARY = "========7d4a6d158c9";
        final String newLine = "\r\n";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = null;
            if (isproxy) {//使用代理模式
                @SuppressWarnings("static-access")
                Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP, new InetSocketAddress(proxyHost,
                    proxyPort));
                conn = (HttpURLConnection) realUrl.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) realUrl.openConnection();
            }
            // 打开和URL之间的连接
            conn.setConnectTimeout(45000);
            conn.setReadTimeout(45000);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // POST方法     
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new DataOutputStream(conn.getOutputStream());
            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append(newLine);
            // 文件参数,photo参数名可以随意修改
            sb.append("Content-Disposition: form-data;name=\"html\";filename=\"" + file.getName()
                      + "\"" + "\r\n");
            sb.append("Content-Type:application/octet-stream"); // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);
            // 发送请求参数
            out.write(sb.toString().getBytes());
            // 数据输入流,用于读取文件数据
            in = new DataInputStream(new FileInputStream(file));
            byte[] bufferOut = new byte[1024 * 8];
            int bytes = 0;
            // 每次读8KB数据,并且将文件数据写入到输出流中 
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            // 最后添加换行 
            out.write(newLine.getBytes());
            in.close();

            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (newLine + "--" + BOUNDARY + "--" + newLine).getBytes();
            // 写上结尾标识 
            out.write(end_data);
            out.flush();
            out.close();

            // 定义BufferedReader输入流来读取URL的响应 
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result += line;
                //这里读取的是上边url对应的上传文件接口的返回值，读取出来后，然后接着返回到前端，实现接口中调用接口的方式 
            }
        } catch (ConnectException e) {
            if (logger.isErrorEnabled()) {
                logger.error("接口连接被关闭或通行协议错误!", e);
            }
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常！", e);
        } finally {//使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                logger.error("发送 POST 请求时关闭输入输出流时异常！", ex);
            }
        }
        return result;
    }
}
