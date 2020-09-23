package com.simple.example.model;


import com.simple.common.error.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveAdvertModel implements Serializable {
    /**  */
    private static final long serialVersionUID = 3156712795707270107L;

    private Integer           id;

    /**
     * 广告标题
     */
    private String            advTitle;

    /**
     * 详细内容
     */
    private String            advDesc;

    /**
     * 广告类型(0:首页Banner  1:资讯)
     */
    private Integer           advType;

    /**
     * 广告图片url
     */
    private String            advPicPath;

    /**
     * 数据来源类型(0:直播房间 1:链接地址)
     */
    private Integer           advSourceType;

    /**
     * 来源(adv_source_type为0时是live_room表ID,为1时是链接地址)
     */
    private String            advSource;

    /**
     * 应用类型(0:微信 1:web)
     */
    private Integer           applicationType;

    /**
     * 开始时间
     */
    private Date              startDate;

    /**
     * 结束时间
     */
    private Date              endDate;

    /**
     * 状态(0:禁用 1:正常 -1:删除)
     */
    private Integer           advStatus;

    /**
     * 排序
     */
    private Integer           advOrder;

    /**
     * 点击量
     */
    private Integer           clickNumber;

    /**
     * 创建时间
     */
    private Date              createdDate;

    /**
     * 创建人
     */
    private String            createdName;

    /**
     * 修改时间
     */
    private Date              updatedDate;

    /**
     * 修改人
     */
    private String            updatedName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdvTitle() {
        return advTitle;
    }

    public void setAdvTitle(String advTitle) {
        this.advTitle = advTitle;
    }

    public String getAdvDesc() {
        return advDesc;
    }

    public void setAdvDesc(String advDesc) {
        this.advDesc = advDesc;
    }

    public Integer getAdvType() {
        return advType;
    }

    public void setAdvType(Integer advType) {
        this.advType = advType;
    }

    public String getAdvPicPath() {
        return advPicPath;
    }

    public void setAdvPicPath(String advPicPath) {
        this.advPicPath = advPicPath;
    }

    public Integer getAdvSourceType() {
        return advSourceType;
    }

    public void setAdvSourceType(Integer advSourceType) {
        this.advSourceType = advSourceType;
    }

    public String getAdvSource() {
        return advSource;
    }

    public void setAdvSource(String advSource) {
        this.advSource = advSource;
    }

    public Integer getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(Integer applicationType) {
        this.applicationType = applicationType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getAdvStatus() {
        return advStatus;
    }

    public void setAdvStatus(Integer advStatus) {
        this.advStatus = advStatus;
    }

    public Integer getAdvOrder() {
        return advOrder;
    }

    public void setAdvOrder(Integer advOrder) {
        this.advOrder = advOrder;
    }

    public Integer getClickNumber() {
        return clickNumber;
    }

    public void setClickNumber(Integer clickNumber) {
        this.clickNumber = clickNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedName() {
        return updatedName;
    }

    public void setUpdatedName(String updatedName) {
        this.updatedName = updatedName;
    }

    /**
     * 添加广告请求参数验证
     * @param advTitle
     * @param advPicPath
     * @param advSource
     * @param startDate
     * @param endDate
     * @param advStatus
     * @param advOrder
     * @throws Exception
     */
    public static void validateAddAdvertParam(String advTitle, String advPicPath, String advSource,
                                              Date startDate, Date endDate, Integer advStatus,
                                              Integer advOrder) throws Exception {
        if (StringUtils.isBlank(advTitle)) {
            throw new ServiceException("广告标题不能为空!");
        }
        if (StringUtils.isBlank(advPicPath)) {
            throw new ServiceException("广告图片不能为空!");
        }
        if (startDate == null) {
            throw new ServiceException("开始时间不能为空!");
        }
        if (endDate == null) {
            throw new ServiceException("结束时间不能为空!");
        }
        if (advStatus == null) {
            throw new ServiceException("状态不能为空!");
        }
        if (advOrder == null) {
            throw new ServiceException("排序不能为空!");
        }
    }

    /**
     * 修改广告请求参数验证
     * @param advTitle
     * @param advPicPath
     * @param advSource
     * @param startDate
     * @param endDate
     * @param advStatus
     * @param advOrder
     * @throws Exception
     */
    public static void validateupdateAdvertParam(Integer id, String advTitle, String advPicPath,
                                                 String advSource, Date startDate, Date endDate,
                                                 Integer advStatus, Integer advOrder)
                                                                                     throws Exception {
        if (id == null || id == 0) {
            throw new ServiceException("广告ID不能为空!");
        }
        if (StringUtils.isBlank(advTitle)) {
            throw new ServiceException("广告标题不能为空!");
        }
        if (StringUtils.isBlank(advPicPath)) {
            throw new ServiceException("广告图片不能为空!");
        }
        if (startDate == null) {
            throw new ServiceException("开始时间不能为空!");
        }
        if (endDate == null) {
            throw new ServiceException("结束时间不能为空!");
        }
        if (advStatus == null) {
            throw new ServiceException("状态不能为空!");
        }
        if (advOrder == null) {
            throw new ServiceException("排序不能为空!");
        }
    }

    /**
     * 添加资讯请求参数验证
     * @param advTitle
     * @param advPicPath
     * @param advSource
     * @param startDate
     * @param endDate
     * @param advStatus
     * @param advOrder
     * @throws Exception
     */
    public static void validateAddInformationParam(String advTitle, String advPicPath,
                                                   String advDesc, Date startDate, Date endDate,
                                                   Integer advStatus, Integer advOrder)
                                                                                       throws Exception {
        if (StringUtils.isBlank(advTitle)) {
            throw new ServiceException("资讯标题不能为空!");
        }
        if (StringUtils.isBlank(advPicPath)) {
            throw new ServiceException("资讯图片不能为空!");
        }
        if (StringUtils.isBlank(advDesc)) {
            throw new ServiceException("资讯内容不能为空!");
        }
        if (startDate == null) {
            throw new ServiceException("开始时间不能为空!");
        }
        if (endDate == null) {
            throw new ServiceException("结束时间不能为空!");
        }
        if (advStatus == null) {
            throw new ServiceException("状态不能为空!");
        }
        if (advOrder == null) {
            throw new ServiceException("排序不能为空!");
        }
    }

    /**
     * 修改资讯请求参数验证
     * @param advTitle
     * @param advPicPath
     * @param advSource
     * @param startDate
     * @param endDate
     * @param advStatus
     * @param advOrder
     * @throws Exception
     */
    public static void validateupdateInformationParam(Integer id, String advTitle,
                                                      String advPicPath,
                                                      String advDesc, Date startDate, Date endDate,
                                                      Integer advStatus, Integer advOrder)
                                                                                          throws Exception {
        if (id == null || id == 0) {
            throw new ServiceException("资讯ID不能为空!");
        }
        if (StringUtils.isBlank(advTitle)) {
            throw new ServiceException("资讯标题不能为空!");
        }
        if (StringUtils.isBlank(advPicPath)) {
            throw new ServiceException("资讯图片不能为空!");
        }
        if (StringUtils.isBlank(advDesc)) {
            throw new ServiceException("资讯内容不能为空!");
        }
        if (startDate == null) {
            throw new ServiceException("开始时间不能为空!");
        }
        if (endDate == null) {
            throw new ServiceException("结束时间不能为空!");
        }
        if (advStatus == null) {
            throw new ServiceException("状态不能为空!");
        }
        if (advOrder == null) {
            throw new ServiceException("排序不能为空!");
        }
    }
}
