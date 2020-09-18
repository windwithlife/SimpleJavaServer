package com.simple.core.interceptor.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.project.meetinglive.common.constant.DataEncode;
import com.simple.core.util.JsonUtil;
import com.simple.core.data.message.ResponseMessage;
import com.simple.core.data.request.JsonMessage;
import com.simple.core.interceptor.annotation.LoginRequired;
//import com.simple.core.token.ValidateLoginHelp;

/**
 * 拦截器,用于控制是否登录
 * @author hejinguo
 * @version $Id: HandlerAuthLoginContextInterceptor.java, v 0.1 2019年11月17日 下午5:37:11
 */
@Component
public class HandlerAuthLoginContextInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory
                                           .getLogger(HandlerAuthLoginContextInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //判断在方法上是否有添加需登录注解(若添加则验证,否则相反)
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        if (methodAnnotation == null) {
            return true;
        }
        try {
            //读取http请求体数据
            InputStream inputStream = request.getInputStream();
            String requestData = StreamUtils.copyToString(inputStream,
                Charset.forName(DataEncode.UTF8));
            if (requestData == null) {
                ResponseMessage resMessage = new ResponseMessage();
                resMessage.setStatus(ResponseMessage.FAILURE_CODE);
                resMessage.setMessage("请求体参数不能为空!");
                returnJsonMessage(response, resMessage);
                return false;
            }
            JsonMessage jsonMessage = JsonUtil.jsonToObject(requestData, JsonMessage.class);
            jsonMessage.setSourceMessage(requestData);

            //用户登录验证
//            ResponseMessage responseMessage = ValidateLoginHelp.validateUserLogin(jsonMessage);
//            if (responseMessage.getStatus() != ResponseMessage.SUCCESS_CODE) {
//                returnJsonMessage(response, responseMessage);
//                return false;
//            } else {
//                return true;
//            }
        } catch (Exception e) {
            logger.error("登录拦截器读取请求体参数信息异常!", e);
        }
        //return false;
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest httpservletrequest,
                           HttpServletResponse httpservletresponse, Object obj,
                           ModelAndView modelandview) throws Exception {

    }

    /**
     * 返回信息
     * @param response
     * @param resMessage
     */
    private void returnJsonMessage(HttpServletResponse response, ResponseMessage resMessage) {
        PrintWriter writer = null;
        response.setCharacterEncoding(DataEncode.UTF8);
        response.setContentType("application/json;charset=UTF-8");
        try {
            writer = response.getWriter();
            writer.print(JsonUtil.writeObjectJSON(resMessage));
        } catch (IOException e) {
            logger.error("登录拦截器中输入返回信息异常!", e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
