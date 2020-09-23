package com.simple.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.core.data.request.JsonMessage;
//import com.simple.core.exception.ServiceException;
import com.simple.common.error.ServiceException;
import com.simple.example.dao.AdvertDao;
import com.simple.example.dto.AdvertVO;
import com.simple.example.dto.BannerVO;

/**
 * 广告资讯业务层
 * @author hejinguo
 * @version $Id: ApiAdvertService.java, v 0.1 2020年7月25日 下午12:04:07
 */
@Service
public class ApiAdvertService {
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
     * 获取资讯详情
     * @param jsonMessage
     * @return
     * @throws Exception
     */
    public AdvertVO getInformationDetail(JsonMessage jsonMessage) throws Exception {
        Integer id = jsonMessage.getInteger("id");
        if (id == null || id == 0) {
            throw new ServiceException("请求参数不能为空!");
        }
        //step2:查询资讯信息
        AdvertVO advertVO = this.advertDao.getInformationDetail(id);
        if (advertVO == null) {
            throw new ServiceException("该资讯信息不存在!");
        }
        return advertVO;
    }

}
