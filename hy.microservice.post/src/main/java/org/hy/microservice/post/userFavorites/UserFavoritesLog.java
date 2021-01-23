package org.hy.microservice.post.userFavorites;

import org.hy.microservice.post.common.BaseViewMode;





/**
 * 用户收藏信息
 *
 * @author      ZhengWei(HY)
 * @createDate  2020-09-07
 * @version     v1.0
 */
public class UserFavoritesLog extends BaseViewMode
{
    
    private static final long serialVersionUID = 7718623462825469760L;
    
    
    
    /** 收藏编号 */
    private String favoritesID;


    
    /**
     * 获取：收藏编号
     */
    public String getFavoritesID()
    {
        return favoritesID;
    }


    
    /**
     * 设置：收藏编号
     * 
     * @param favoritesID 
     */
    public void setFavoritesID(String favoritesID)
    {
        this.favoritesID = favoritesID;
    }
    
}
