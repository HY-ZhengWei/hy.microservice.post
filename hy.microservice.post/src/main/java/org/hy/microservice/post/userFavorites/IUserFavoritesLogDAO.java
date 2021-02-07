package org.hy.microservice.post.userFavorites;

import org.hy.common.xml.annotation.XType;
import org.hy.common.xml.annotation.Xjava;
import org.hy.common.xml.annotation.Xsql;





/**
 * 用户收藏信息的DAO
 *
 * @author      ZhengWei(HY)
 * @createDate  2020-10-19
 * @version     v1.0
 */
@Xjava(id="UserFavoritesLogDAO" ,value=XType.XSQL)
public interface IUserFavoritesLogDAO
{
    
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
    @Xsql("XSQL_UserFavoritesLog_Insert")
    public int addLog(UserFavoritesLog i_UserFavoritesLog);

    

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
    @Xsql("XSQL_UserFavoritesLog_Del")
    public int delLog(UserFavoritesLog i_UserFavoritesLog);

}