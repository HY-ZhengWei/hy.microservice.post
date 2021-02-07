package org.hy.microservice.post.userFavorites;

import org.hy.common.xml.annotation.Xjava;





/**
 * 用户收藏的业务层
 *
 * @author      ZhengWei(HY)
 * @createDate  2020-10-19
 * @version     v1.0
 */
@Xjava
public class UserFavoritesLogService
{
    
    @Xjava
    private IUserFavoritesLogDAO userFavoritesLogDAO;
    
    
    
    /**
     * 添加用户收藏
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-19
     * @version     v1.0
     *
     * @param i_UserFavoritesLog
     * @return
     */
    public boolean addLog(UserFavoritesLog i_UserFavoritesLog)
    {
        return this.userFavoritesLogDAO.addLog(i_UserFavoritesLog) == 1;
    }

    

    /**
     * 取消用户收藏
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-09-19
     * @version     v1.0
     *
     * @param i_UserFavoritesLog
     * @return
     */
    public boolean delLog(UserFavoritesLog i_UserFavoritesLog)
    {
        return this.userFavoritesLogDAO.delLog(i_UserFavoritesLog) == 1;
    }
    
}
