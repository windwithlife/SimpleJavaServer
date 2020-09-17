package com.project.meetinglive.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

public class BaseUtil {
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
        return attrs.getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletWebRequest attrs = (ServletWebRequest) RequestContextHolder.getRequestAttributes();
        return attrs.getResponse();
    }

    protected static HttpSession getSession() {
        return getRequest().getSession();
    }

    protected static String getRealPath() {
        return getSession().getServletContext().getRealPath("/");
    }

    public static String getTempFoderPath() {
        return getRealPath() + "temp";
    }

}
