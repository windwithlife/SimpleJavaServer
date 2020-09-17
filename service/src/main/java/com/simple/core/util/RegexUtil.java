package com.project.meetinglive.common.util;

import java.util.regex.Pattern;

public class RegexUtil {
    /**
     * 身份证正则验证
     * @param cardid
     * @return
     */
    public static boolean isCardId(String cardid) {
        return Pattern.matches(
            "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$",
            cardid);
    }

}
