package org.hy.microservice.post.userOpen;

import org.hy.microservice.common.BaseViewMode;





/**
 * 用户阅读信息
 *
 * @author      ZhengWei(HY)
 * @createDate  2020-10-21
 * @version     v1.0
 */
public class UserOpenLog extends BaseViewMode
{
    
    private static final long serialVersionUID = 7718623462825469760L;
    
    
    
    /** 阅读编号 */
    private String openID;


    
    /**
     * 获取：阅读编号
     */
    public String getOpenID()
    {
        return openID;
    }


    
    /**
     * 设置：阅读编号
     * 
     * @param openID 
     */
    public void setOpenID(String openID)
    {
        this.openID = openID;
    }
    
}
