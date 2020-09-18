package com.simple.example.dao;

import com.simple.example.dto.AdvertVO;
import com.simple.example.dto.BannerVO;
import com.simple.example.model.LiveAdvertModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 广告资讯数据层
 * @author hejinguo
 * @version $Id: AdvertDao.java, v 0.1 2020年7月25日 下午12:27:03
 */
@Mapper
public interface AdvertDao {

    /**
     * 获取首页Baner信息
     * @return
     * @throws Exception
     */
    List<BannerVO> gethomePageBanner() throws Exception;

    /**
     * 查询资讯信息
     * @return
     * @throws Exception
     */
    List<AdvertVO> getInformationList() throws Exception;

    /**
     * 获取资讯详情
     * @param id
     * @return
     * @throws Exception
     */
    AdvertVO getInformationDetail(@Param("id") Integer id) throws Exception;

    /**
     * 分页获取全部广告信息总记录数
     * @param paramMap
     * @return
     * @throws Exception
     */
    int getAdvertListCount(Map<String, Object> paramMap) throws Exception;

    /**
     * 分页获取全部广告信息
     * @return
     * @throws Exception
     */
    List<BannerVO> getAdvertList(Map<String, Object> paramMap) throws Exception;

    /**
     * 添加广告信息
     * @param advertModel
     * @throws Exception
     */
    void addAdvert(LiveAdvertModel advertModel) throws Exception;

    /**
     * 根据ID查询广告信息
     * @param id
     * @return
     * @throws Exception
     */
    LiveAdvertModel getLiveAdvertById(@Param("id") Integer id) throws Exception;

    /**
     * 修改广告信息
     * @param advertModel
     * @throws Exception
     */
    void updateAdvert(LiveAdvertModel advertModel) throws Exception;

    /**
     * 删除广告信息
     * @param paramMap
     * @throws Exception
     */
    void deleteAdvert(Map<String, Object> paramMap) throws Exception;

}
