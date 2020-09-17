/**
 * DianMei.com Inc.
 * Copyright (c) 2015-2018 All Rights Reserved.
 */
package com.project.meetinglive.common.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 字符串工具类
 * @author hejinguo
 * @version $Id: StringUtil.java, v 0.1 2019年11月17日 下午4:56:27
 */
public class StringUtil {
    /**
     * 获取随机验证码
     * @param length
     * @return
     */
    public static String getRadomCode(int length) {
        final int maxNum = 36;
        int i; //生成的随机数
        int count = 0; //生成的密码的长度
        char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < length) {
            i = Math.abs(r.nextInt(maxNum)); //生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    /**
     * 根据长度生成随机数字
     * @param length
     * @return
     */
    public static String getRandomNum(int length) { //length表示生成字符串的长度  
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 验证是否全部为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 去除空格、回车、换行符、制表符
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 去除回车、换行符、制表符
     * @param str
     * @return
     */
    public static String replaceNoBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 去除回车、制表符
     * @param str
     * @return
     */
    public static String replaceTRBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 字符串删除只保留第一个换行符
     * @param str
     * @return
     */
    public static String handleSlogan(String str) {
        String dest = "";
        if (StringUtils.isNotBlank(str)) {
            Pattern p = Pattern.compile("\\t|\r");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
            dest = dest.replaceAll("\r|\n", "\n");
            int num = 0;
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < dest.length(); i++) {
                char charWord = dest.charAt(i);
                if (num == 0 || charWord != 10) {
                    sb.append(charWord);
                }
                if (num == 0 && charWord == 10) {
                    num++;
                }
            }
            dest = sb.toString();
        }
        return dest;
    }
    
    /**
     * 处理H5页面换行符需要转成<br>
     * @param str
     * @return
     */
    public static String handleH5Slogan(String str) {
        String dest = "";
        if (StringUtils.isNotBlank(str)) {
            dest = str.replaceAll("\r|\n", "<br>");
        }
        return dest;
    }

    public static void main(String[] args) {
        String dest = "美一天，从预约" + "\n\r" + "吧吧" + "\n\r" + "吧吧哈哈哈开始";
        System.out.println(dest);
        System.out.println("-------------------------------------");
        System.out.println(handleSlogan(dest));
    }
}
