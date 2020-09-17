package com.project.meetinglive.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * 文件名帮助类
 * @author hejinguo
 * @version $Id: FileNameRuleHelp.java, v 0.1 2019年11月20日 下午2:53:06
 */
public class FileNameRuleHelp {
    /**
     * 获取用户头像图片名
     * @return
     */
    public static String getUserPictureFileName() {
        DateFormat dataFormat = new SimpleDateFormat("yyMMddHHmmss");
        Date now = Calendar.getInstance().getTime();
        return "images" + dataFormat.format(now) + new Random().nextInt(99999);
    }

    /**生成文件路径：年份/月/日/   */
    public static String makeTargetPath() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int year = calendar.get(Calendar.YEAR);//年
        int month = calendar.get(Calendar.MONTH) + 1;//月
        int day = calendar.get(Calendar.DATE);

        return year + "/" + String.format("%02d", month) + "/" + String.format("%02d", day) + "/";
    }

}
