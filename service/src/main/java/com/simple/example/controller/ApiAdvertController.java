package com.simple.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.simple.example.service.ApiAdvertService;

import com.simple.core.data.message.ResponseMessage;
import com.simple.core.data.pageBean.SinglePageBean;
import com.simple.core.data.request.JsonMessage;
import com.simple.core.exception.CommonExceptionHandle;

import com.simple.example.dto.AdvertVO;
import com.simple.example.dto.BannerVO;


/**
 * 广告资讯业务控制层
 * @author hejinguo
 * @version $Id: ApiAdvertController.java, v 0.1 2020年7月25日 下午12:03:02
 */
@RestController
@RequestMapping("/advertService/mobile")
public class ApiAdvertController {
    private static final Logger logger = LoggerFactory.getLogger(ApiAdvertController.class);
    @Autowired
    private ApiAdvertService apiAdvertService;
//    @Autowired
//    private ApiRoomService      apiRoomService;

    /**
     * 获取首页信息
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
//    @PostMapping(value = { "/getHomePage" }, consumes = { "application/json" }, produces = { "application/json" })
//    public @ResponseBody ResponseMessage getHomePage(@RequestBody JsonMessage jsonMessage,
//                                                      HttpServletRequest request,
//                                                      HttpServletResponse response) {
//        //返回对象
//        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
//        try {
//            //step1:获取首页Baner信息
//            List<BannerVO> pageBannerList = this.apiAdvertService.gethomePageBanner();
//            //step2:获取首页直播速递
//            List<LiveRoomVO> liveStartList = this.apiRoomService.getLiveStartList();
//            //step3:获取直播历史信息
//            List<LiveRoomVO> liveHistoryList = this.apiRoomService.getLiveHistoryList();
//            //step4:返回结果
//            resMessage.addBeanList("pageBannerList", pageBannerList);
//            resMessage.addBeanList("liveStartList", liveStartList);
//            resMessage.addBeanList("liveHistoryList", liveHistoryList);
//            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
//            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
//        } catch (Exception e) {
//            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
//        }
//        return resMessage;
//    }

    /**
     * 获取资讯信息
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/getInformationList" }, consumes = { "application/json" }, produces = { "application/json" })
    public @ResponseBody ResponseMessage getInformationList(@RequestBody JsonMessage jsonMessage,
                                                            HttpServletRequest request,
                                                            HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            //step1:获取首页Baner信息
            List<AdvertVO> informationList = this.apiAdvertService.getInformationList();
            //step4:返回结果
            resMessage.addBeanList("informationList", informationList);
            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 获取资讯详情
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/getInformationDetail" }, consumes = { "application/json" }, produces = { "application/json" })
    public @ResponseBody ResponseMessage getInformationDetail(@RequestBody JsonMessage jsonMessage,
                                                              HttpServletRequest request,
                                                              HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            //step1:获取资讯详情
            AdvertVO advertVO = this.apiAdvertService.getInformationDetail(jsonMessage);
            //step2:返回结果
            resMessage.addKey$Value("id", advertVO.getId());
            resMessage.addKey$Value("advTitle", advertVO.getAdvTitle());
            resMessage.addKey$Value("advPicPath", advertVO.getAdvPicPath());
            resMessage.addKey$Value("advDesc", advertVO.getAdvDesc());
            resMessage.addKey$Value("advSource", advertVO.getAdvSource());
            resMessage.addKey$Value("clickNumber", advertVO.getClickNumber());
            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 获取会议列表
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
//    @PostMapping(value = { "/getLiveList" }, consumes = { "application/json" }, produces = { "application/json" })
//    public @ResponseBody ResponseMessage getLiveList(@RequestBody JsonMessage jsonMessage,
//                                                     HttpServletRequest request,
//                                                     HttpServletResponse response) {
//        //返回对象
//        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
//        try {
//            SinglePageBean<LiveListVO> liveList = this.apiRoomService.getLiveList(jsonMessage);
//            resMessage.addBeanList("liveList", liveList.getList() != null ? liveList.getList()
//                : new ArrayList<LiveListVO>());
//            resMessage.addKey$Value("currentPage", liveList.getCurrentPage());
//            resMessage.addKey$Value("totalPage", liveList.getTotalPage());
//            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
//            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
//        } catch (Exception e) {
//            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
//        }
//        return resMessage;
//    }

    /**
     * 获取会议详情
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
//    @PostMapping(value = { "/getLiveDetail" }, consumes = { "application/json" }, produces = { "application/json" })
//    public @ResponseBody ResponseMessage getLiveDetail(@RequestBody JsonMessage jsonMessage,
//                                                       HttpServletRequest request,
//                                                       HttpServletResponse response) {
//        //返回对象
//        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
//        try {
//            //step1:获取直播详情
//            LiveRoomModel liveRoomModel = this.apiRoomService.getLiveDetail(jsonMessage);
//            //step2:返回结果
//            resMessage.addKey$Value("id", liveRoomModel.getId());
//            resMessage.addKey$Value("roomTitle", liveRoomModel.getRoomTitle());
//            resMessage.addKey$Value("roomPicPath", liveRoomModel.getRoomPicPath());
//            resMessage.addKey$Value("roomQrCodePath", liveRoomModel.getRoomQrCodePath());
//            resMessage.addKey$Value("roomSchedulePath", liveRoomModel.getRoomSchedulePath());
//            resMessage.addKey$Value("roomDescPath", liveRoomModel.getRoomDescPath());
//            resMessage.addKey$Value("roomDesc", liveRoomModel.getRoomDesc());
//            resMessage.addKey$Value("liveStartDate", liveRoomModel.getLiveStartDate());
//            resMessage.addKey$Value("playNumber", liveRoomModel.getPlayNumber());
//            resMessage.addKey$Value("pushServerUrl", liveRoomModel.getPushServerUrl());
//            resMessage.addKey$Value("pushSecretKey", liveRoomModel.getPushSecretKey());
//            resMessage.addKey$Value("videoMp4Url", liveRoomModel.getVideoMp4Url());
//            resMessage.addKey$Value("roomStatus", liveRoomModel.getRoomStatus());
//            resMessage.addKey$Value("publishStatus", liveRoomModel.getPublishStatus());
//            resMessage.addKey$Value("pullHlsUrl", liveRoomModel.getPullHlsUrl());
//            resMessage.addKey$Value("pullRtmpUrl", liveRoomModel.getPullRtmpUrl());
//            resMessage.addKey$Value("pullFlvUrl", liveRoomModel.getPullFlvUrl());
//            resMessage.addKey$Value("pullUdpUrl", liveRoomModel.getPullUdpUrl());
//            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
//            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
//        } catch (Exception e) {
//            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
//        }
//        return resMessage;
//    }


}
