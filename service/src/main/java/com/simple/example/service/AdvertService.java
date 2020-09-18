package com.simple.example.service;

import com.simple.example.dao.AdvertDao;
import com.simple.example.dto.AdvertVO;
import com.simple.example.dto.BannerVO;
import com.simple.example.model.LiveAdvertModel;
import com.simple.core.data.pageBean.SinglePageBean;
import com.simple.core.data.request.JsonMessage;
//import com.simple.core.exception.ServiceException;
import com.simple.common.error.ServiceException;
import com.simple.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 广告资讯业务层
 * @author hejinguo
 * @version $Id: ApiAdvertService.java, v 0.1 2020年7月25日 下午12:04:07
 */
@Service
public class AdvertService {
    @Autowired
    private AdvertDao advertDao;

    /**
     * 获取首页Baner信息
     * @return
     * @throws Exception
     */
    public List<BannerVO> gethomePageBanner() throws Exception {
        return this.advertDao.gethomePageBanner();
    }

    /**
     * 查询资讯信息
     * @return
     * @throws Exception
     */
    public List<AdvertVO> getInformationList() throws Exception {
        return this.advertDao.getInformationList();
    }

    /**
     * 获取广告列表信息
     * @return
     * @throws Exception
     */
    public SinglePageBean<BannerVO> getAdvertList(JsonMessage jsonMessage) throws Exception {
        //step1:返回对象
        SinglePageBean<BannerVO> response = new SinglePageBean<BannerVO>();
        //step2:获取请求参数
        Integer currentPage = jsonMessage.getInt("currentPage");
        Integer pageSize = jsonMessage.getInt("pageSize");
        response.setCurrentPage(currentPage);
        response.setPageSize(pageSize);
        //step3:设置请求参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("advType", 0);
        int totalCount = this.advertDao.getAdvertListCount(paramMap);
        //step4:设置分页参数信息
        response.setTotalItems(totalCount);
        response.countAllPage();
        //step5:查询广告信息
        if (totalCount > 0) {
            paramMap.put("pageSize", response.getPageSize());
            paramMap.put("startIndex", response.gainStartIndex());
            List<BannerVO> listBanner=this.advertDao.getAdvertList(paramMap);
            response.setList(listBanner);
        }
        return response;
    }

    /**
     * 添加广告
     * @param jsonMessage
     * @throws Exception
     */
    public void addAdvert(JsonMessage jsonMessage) throws Exception {
        //step1：获取请求参数
        String advTitle = jsonMessage.getString("advTitle");
        String advPicPath = jsonMessage.getString("advPicPath");
        String advSource = jsonMessage.getString("advSource");
        Date startDate = DateUtil.StringToDate(jsonMessage.getString("startDate"));
        Date endDate = DateUtil.StringToDate(jsonMessage.getString("endDate"));
        Integer advStatus = jsonMessage.getInteger("advStatus");
        Integer advOrder = jsonMessage.getInteger("advOrder");
        LiveAdvertModel.validateAddAdvertParam(advTitle, advPicPath, advSource, startDate, endDate,
            advStatus, advOrder);
        //step2:封装数据保存信息
        LiveAdvertModel advertModel = new LiveAdvertModel();
        advertModel.setAdvTitle(advTitle);
        advertModel.setAdvType(0);
        advertModel.setAdvPicPath(advPicPath);
        advertModel.setAdvSource(advSource);
        advertModel.setAdvSourceType(1);
        advertModel.setApplicationType(0);
        advertModel.setStartDate(startDate);
        advertModel.setEndDate(endDate);
        advertModel.setAdvStatus(advStatus);
        advertModel.setAdvOrder(advOrder);
        advertModel.setClickNumber(0);
        this.advertDao.addAdvert(advertModel);
    }

    /**
     * 修改广告
     * @param jsonMessage
     * @throws Exception
     */
    public void updateAdvert(JsonMessage jsonMessage) throws Exception {
        //step1：获取请求参数
        Integer id = jsonMessage.getInteger("id");
        String advTitle = jsonMessage.getString("advTitle");
        String advPicPath = jsonMessage.getString("advPicPath");
        String advSource = jsonMessage.getString("advSource");
        Date startDate = DateUtil.StringToDate(jsonMessage.getString("startDate"));
        Date endDate = DateUtil.StringToDate(jsonMessage.getString("endDate"));
        Integer advStatus = jsonMessage.getInteger("advStatus");
        Integer advOrder = jsonMessage.getInteger("advOrder");
        LiveAdvertModel.validateupdateAdvertParam(id, advTitle, advPicPath, advSource, startDate,
            endDate,
            advStatus, advOrder);
        //step2:查询广告信息
        LiveAdvertModel advertModel = this.advertDao.getLiveAdvertById(id);
        if (advertModel == null) {
            throw new ServiceException("广告信息不存在!");
        }
        //step3：修改广告信息
        advertModel.setAdvTitle(advTitle);
        advertModel.setAdvPicPath(advPicPath);
        advertModel.setAdvSource(advSource);
        advertModel.setStartDate(startDate);
        advertModel.setEndDate(endDate);
        advertModel.setAdvStatus(advStatus);
        advertModel.setAdvOrder(advOrder);
        this.advertDao.updateAdvert(advertModel);
    }
    
    /**
     * 删除广告
     * @param jsonMessage
     * @throws Exception
     */
    public void deleteAdvert(JsonMessage jsonMessage) throws Exception {
        //step1：获取请求参数
        Integer id = jsonMessage.getInteger("id");
        if (id == null || id == 0) {
            throw new ServiceException("广告ID不能为空!");
        }
        //step2:查询广告信息
        LiveAdvertModel advertModel = this.advertDao.getLiveAdvertById(id);
        if (advertModel == null) {
            throw new ServiceException("广告信息不存在!");
        }
        //step3:删除广告
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("advStatus", -1);
        this.advertDao.deleteAdvert(paramMap);
    }

    /**
     * 查询pc资讯列表信息
     * @return
     * @throws Exception
     */
    public SinglePageBean<BannerVO> getPcInformationList(JsonMessage jsonMessage) throws Exception {
        //step1:返回对象
        SinglePageBean<BannerVO> response = new SinglePageBean<BannerVO>();
        //step2:获取请求参数
        Integer currentPage = jsonMessage.getInt("currentPage");
        Integer pageSize = jsonMessage.getInt("pageSize");
        response.setCurrentPage(currentPage);
        response.setPageSize(pageSize);
        //step3:设置请求参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("advType", 1);
        int totalCount = this.advertDao.getAdvertListCount(paramMap);
        //step4:设置分页参数信息
        response.setTotalItems(totalCount);
        response.countAllPage();
        //step5:查询广告信息
        if (totalCount > 0) {
            paramMap.put("pageSize", response.getPageSize());
            paramMap.put("startIndex", response.gainStartIndex());
            List<BannerVO> listBanner = this.advertDao.getAdvertList(paramMap);
            response.setList(listBanner);
        }
        return response;
    }
    
    /**
     * 添加资讯信息
     * @param jsonMessage
     * @throws Exception
     */
    public void addInformation(JsonMessage jsonMessage) throws Exception {
        //step1：获取请求参数
        String advTitle = jsonMessage.getString("advTitle");
        String advPicPath = jsonMessage.getString("advPicPath");
        String advDesc = jsonMessage.getString("advDesc");
        Date startDate = DateUtil.StringToDate(jsonMessage.getString("startDate"));
        Date endDate = DateUtil.StringToDate(jsonMessage.getString("endDate"));
        Integer advStatus = jsonMessage.getInteger("advStatus");
        Integer advOrder = jsonMessage.getInteger("advOrder");
        LiveAdvertModel.validateAddInformationParam(advTitle, advPicPath, advDesc,
            startDate, endDate,
            advStatus, advOrder);
        //step2:生成html页面
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("advDesc", advDesc);
        //String fileUrl = FreeMarkerHelp.makeHtml("information.ftl", paramMap);
        //step2:封装数据保存信息
        LiveAdvertModel advertModel = new LiveAdvertModel();
        advertModel.setAdvTitle(advTitle);
        advertModel.setAdvType(1);
        advertModel.setAdvPicPath(advPicPath);
        advertModel.setAdvDesc(advDesc);
        advertModel.setAdvSourceType(1);
        //advertModel.setAdvSource(fileUrl);
        advertModel.setApplicationType(0);
        advertModel.setStartDate(startDate);
        advertModel.setEndDate(endDate);
        advertModel.setAdvStatus(advStatus);
        advertModel.setAdvOrder(advOrder);
        advertModel.setClickNumber(0);
        this.advertDao.addAdvert(advertModel);
    }

    public void updateInformation(JsonMessage jsonMessage) throws Exception {
        //step1：获取请求参数
        Integer id = jsonMessage.getInteger("id");
        String advTitle = jsonMessage.getString("advTitle");
        String advPicPath = jsonMessage.getString("advPicPath");
        String advDesc = jsonMessage.getString("advDesc");
        Date startDate = DateUtil.StringToDate(jsonMessage.getString("startDate"));
        Date endDate = DateUtil.StringToDate(jsonMessage.getString("endDate"));
        Integer advStatus = jsonMessage.getInteger("advStatus");
        Integer advOrder = jsonMessage.getInteger("advOrder");
        LiveAdvertModel.validateupdateInformationParam(id, advTitle, advPicPath, advDesc,
            startDate,
            endDate, advStatus, advOrder);
        //step2:查询广告信息
        LiveAdvertModel advertModel = this.advertDao.getLiveAdvertById(id);
        if (advertModel == null || advertModel.getAdvType() != 1) {
            throw new ServiceException("资讯信息不存在!");
        }

        //step3：修改资讯信息
//        String fileUrl = null;
//        if (!advDesc.equals(advertModel.getAdvDesc())) {
//            Map<String, Object> paramMap = new HashMap<String, Object>();
//            paramMap.put("advDesc", advDesc);
//            fileUrl = FreeMarkerHelp.makeHtml("information.ftl", paramMap);
//        } else {
//            fileUrl = advertModel.getAdvSource();
//        }
        advertModel.setAdvTitle(advTitle);
        advertModel.setAdvPicPath(advPicPath);
        //advertModel.setAdvSource(fileUrl);
        advertModel.setAdvDesc(advDesc);
        advertModel.setStartDate(startDate);
        advertModel.setEndDate(endDate);
        advertModel.setAdvStatus(advStatus);
        advertModel.setAdvOrder(advOrder);
        this.advertDao.updateAdvert(advertModel);
    }

    public void deleteInformation(JsonMessage jsonMessage) throws Exception {
        //step1：获取请求参数
        Integer id = jsonMessage.getInteger("id");
        if (id == null || id == 0) {
            throw new ServiceException("资讯ID不能为空!");
        }
        //step2:查询资讯信息
        LiveAdvertModel advertModel = this.advertDao.getLiveAdvertById(id);
        if (advertModel == null) {
            throw new ServiceException("资讯信息不存在!");
        }
        //step3:删除资讯
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("advStatus", -1);
        this.advertDao.deleteAdvert(paramMap);
    }

}
