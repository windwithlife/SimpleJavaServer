package com.simple.example.dao;

import com.simple.example.dto.BannerVO;
import com.simple.example.model.ExampleModel;
import com.simple.example.model.LiveAdvertModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * 使用Mybatis SQl Java 注解的方式方式进行数据访问的样例
 * @author zhangyq
 * @version v1.0 ExampleRepo.java
 */

@Mapper
public interface ExampleDao {



    ExampleModel findByName(String name) throws Exception;

    /**
     * 分页获取全部总记录数
     * @param paramMap
     * @return
     * @throws Exception
     */
    int getAdvertListCount(Map<String, Object> paramMap) throws Exception;

    /**
     * 分页获取全部
     * @return
     * @throws Exception
     */
    List<BannerVO> getAdvertList(Map<String, Object> paramMap) throws Exception;

    /**
     * 添加
     * @param advertModel
     * @throws Exception
     */
    void add(ExampleModel advertModel) throws Exception;

    /**
     * 根据ID查询
     * @param id
     * @return
     * @throws Exception
     */
    LiveAdvertModel getLiveAdvertById(@Param("id") Integer id) throws Exception;

    /**
     * 修改
     * @param advertModel
     * @throws Exception
     */
    void updateAdvert(LiveAdvertModel advertModel) throws Exception;

    /**
     * 删除
     * @param paramMap
     * @throws Exception
     */
    void deleteAdvert(Map<String, Object> paramMap) throws Exception;


}
