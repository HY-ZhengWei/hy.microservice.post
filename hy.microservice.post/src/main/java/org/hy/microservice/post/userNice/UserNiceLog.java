package org.hy.microservice.post.userNice;

import org.hy.microservice.common.BaseViewMode;





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

    /** 点赞对象的编号 */
    private String niceID;
    
    /** 点赞对象的类型 */
    private String niceType;
    
    /** 设备号 */
    private String deviceNo;

    /** 设备类型 */
    private String deviceType;
    
    /** 点赞次数 */
    private Long   niceCount;
    
    /** 我是否点赞过 */
    private Long   myIsNice;
    
    /** 查看点赞的用户ID */
    private String seeUserID; 
    
    
    
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


    
    /**
     * 获取：点赞次数
     */
    public Long getNiceCount()
    {
        return niceCount;
    }


    
    /**
     * 获取：我是否点赞过
     */
    public Long getMyIsNice()
    {
        return myIsNice;
    }


    
    /**
     * 设置：点赞次数
     * 
     * @param niceCount 
     */
    public void setNiceCount(Long niceCount)
    {
        this.niceCount = niceCount;
    }


    
    /**
     * 设置：我是否点赞过
     * 
     * @param myIsNice 
     */
    public void setMyIsNice(Long myIsNice)
    {
        this.myIsNice = myIsNice;
    }


    
    /**
     * 获取：查看点赞的用户ID
     */
    public String getSeeUserID()
    {
        return seeUserID;
    }


    
    /**
     * 设置：查看点赞的用户ID
     * 
     * @param seeUserID 
     */
    public void setSeeUserID(String seeUserID)
    {
        this.seeUserID = seeUserID;
    }
    
}
