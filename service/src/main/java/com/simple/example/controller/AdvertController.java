package com.simple.example.controller;

import com.simple.example.dto.BannerVO;
import com.simple.example.service.AdvertService;
import com.simple.core.data.message.ResponseMessage;
import com.simple.core.data.pageBean.SinglePageBean;
import com.simple.core.data.request.JsonMessage;
import com.simple.core.exception.CommonExceptionHandle;
import com.simple.core.interceptor.annotation.LoginRequired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * 广告资讯业务控制层
 * @author hejinguo
 * @version $Id: ApiAdvertController.java, v 0.1 2020年7月25日 下午12:03:02
 */
@RestController
@RequestMapping("/pc/advertService")
public class AdvertController {
    private static final Logger logger = LoggerFactory.getLogger(AdvertController.class);
    @Autowired
    private AdvertService       advertService;


    /**
     * 获取广告列表列表 
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/getAdvertList" }, consumes = { "application/json" }, produces = { "application/json" })
    @LoginRequired
    public @ResponseBody ResponseMessage getAdvertList(@RequestBody JsonMessage jsonMessage,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            SinglePageBean<BannerVO> bannerList = this.advertService.getAdvertList(jsonMessage);
            resMessage.addBeanList("advertList",
                bannerList.getList() != null ? bannerList.getList() : new ArrayList<BannerVO>());
            resMessage.addKey$Value("currentPage", bannerList.getCurrentPage());
            resMessage.addKey$Value("totalPage", bannerList.getTotalPage());
            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 添加广告
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/addAdvert" }, consumes = { "application/json" }, produces = { "application/json" })
    @LoginRequired
    public @ResponseBody ResponseMessage addAdvert(@RequestBody JsonMessage jsonMessage,
                                                            HttpServletRequest request,
                                                            HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            this.advertService.addAdvert(jsonMessage);
            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }


    /**
     * 修改广告
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/updateAdvert" }, consumes = { "application/json" }, produces = { "application/json" })
    @LoginRequired
    public @ResponseBody ResponseMessage updateAdvert(@RequestBody JsonMessage jsonMessage,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            this.advertService.updateAdvert(jsonMessage);
            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 删除广告
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/deleteAdvert" }, consumes = { "application/json" }, produces = { "application/json" })
    @LoginRequired
    public @ResponseBody ResponseMessage deleteAdvert(@RequestBody JsonMessage jsonMessage,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            this.advertService.deleteAdvert(jsonMessage);
            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 获取资讯列表
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/getInformationList" }, consumes = { "application/json" }, produces = { "application/json" })
    @LoginRequired
    public @ResponseBody ResponseMessage getInformationList(@RequestBody JsonMessage jsonMessage,
                                                            HttpServletRequest request,
                                                            HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            SinglePageBean<BannerVO> bannerList = this.advertService
                .getPcInformationList(jsonMessage);
            resMessage.addBeanList("informationList",
                bannerList.getList() != null ? bannerList.getList() : new ArrayList<BannerVO>());
            resMessage.addKey$Value("currentPage", bannerList.getCurrentPage());
            resMessage.addKey$Value("totalPage", bannerList.getTotalPage());
            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 添加资讯
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/addInformation" }, consumes = { "application/json" }, produces = { "application/json" })
    @LoginRequired
    public @ResponseBody ResponseMessage addInformation(@RequestBody JsonMessage jsonMessage,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            this.advertService.addInformation(jsonMessage);
            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 修改资讯
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/updateInformation" }, consumes = { "application/json" }, produces = { "application/json" })
    @LoginRequired
    public @ResponseBody ResponseMessage updateInformation(@RequestBody JsonMessage jsonMessage,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            this.advertService.updateInformation(jsonMessage);
            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 删除资讯
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/deleteInformation" }, consumes = { "application/json" }, produces = { "application/json" })
    @LoginRequired
    public @ResponseBody ResponseMessage deleteInformation(@RequestBody JsonMessage jsonMessage,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            this.advertService.deleteInformation(jsonMessage);
            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }


}
