package org.hy.microservice.post.userNice;

import org.hy.microservice.post.common.BaseViewMode;





/**
 * 点赞信息
 *
 * @author      ZhengWei(HY)
 * @createDate  2020-09-07
 * @version     v1.0
 */
public class UserNiceLog extends BaseViewMode
{
    
    private static final long serialVersionUID = -2489682868611553092L;

    /** 用户编号 */
    private String userID;
    
    /** 点赞对象的编号 */
    private String niceID;
    
    /** 点赞对象的类型 */
    private String niceType;
    
    /** 设备号 */
    private String deviceNo;

    /** 设备类型 */
    private String deviceType;
    
    
    
    /**
     * 获取：用户编号
     */
    public String getUserID()
    {
        return userID;
    }

    
    /**
     * 获取：点赞对象的编号
     */
    public String getNiceID()
    {
        return niceID;
    }

    
    /**
     * 获取：点赞对象的类型
     */
    public String getNiceType()
    {
        return niceType;
    }


    
    /**
     * 设置：用户编号
     * 
     * @param userID 
     */
    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    
    /**
     * 设置：点赞对象的编号
     * 
     * @param niceID 
     */
    public void setNiceID(String niceID)
    {
        this.niceID = niceID;
    }

    
    /**
     * 设置：点赞对象的类型
     * 
     * @param niceType 
     */
    public void setNiceType(String niceType)
    {
        this.niceType = niceType;
    }

    /**
     * 获取：设备号
     */
    public String getDeviceNo()
    {
        return deviceNo;
    }



    /**
     * 设置：设备号
     *
     * @param deviceNo
     */
    public void setDeviceNo(String deviceNo)
    {
        this.deviceNo = deviceNo;
    }



    /**
     * 获取：设备类型
     */
    public String getDeviceType()
    {
        return deviceType;
    }



    /**
     * 设置：设备类型
     *
     * @param deviceType
     */
    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }
}
