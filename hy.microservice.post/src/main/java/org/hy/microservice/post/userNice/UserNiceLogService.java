package org.hy.microservice.post.userNice;

import java.util.List;

import org.hy.common.xml.annotation.Xjava;





/**
 * @author LHao
 * @version [v1.0]
 * @ClassName: UserNiceLogService
 * @Description: 用户点赞Service 类
 * @date 2020/10/15 21:13
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Xjava
public class UserNiceLogService 
{
    
    @Xjava
    public IUserNiceLogDAO userNiceLogDAO;
    


    /**
     * 点赞（加）
     *
     * @author      LiHao
     * @createDate  2020-10-15
     * @version     v1.0
     *
     * @param i_UserNiceLog
     * @return
     */
    public boolean goodCountAdd(UserNiceLog i_UserNiceLog)
    {
        if ( this.userNiceLogDAO.addLog(i_UserNiceLog) >= 1 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    /**
     * 更新美好回忆的点赞（减）
     *
     * @author      ZhengWei(HY)
     * @createDate  2020-08-18
     * @version     v1.0
     *
     * @param i_UserNiceLog
     * @return
     */
    public boolean goodCountSubtract(UserNiceLog i_UserNiceLog)
    {
        if ( this.userNiceLogDAO.delLog(i_UserNiceLog) >= 1 )
        {
            return true;

        }
        else
        {
            return false;
        }
    }
    
    

    /**
     * 取消点赞
     *
     * @author      LiHao
     * @createDate  2020-10-15
     * @version     v1.0
     *
     * @param i_UserNiceLog
     * @return
     */
    public int delLog(UserNiceLog i_UserNiceLog)
    {
        return userNiceLogDAO.delLog(i_UserNiceLog);
    }

    

    /**
     * 查询点赞对象的点赞数量
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-02-07
     * @version     v1.0
     *
     * @param i_UserNiceLog
     * @return
     */
    public Long queryNiceCount(UserNiceLog i_UserNiceLog)
    {
        return this.userNiceLogDAO.queryNiceCount(i_UserNiceLog);
    }
    
    
    
    /**
     * 查询点赞列表
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-02-08
     * @version     v1.0
     *
     * @param i_UserNiceLog
     * @return
     */
    public List<UserNiceLog> queryNices(UserNiceLog i_UserNiceLog)
    {
        return this.userNiceLogDAO.queryNices(i_UserNiceLog);
    }
    
    
    
    /**
     * 点赞列表查询的总行数
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-02-08
     * @version     v1.0
     *
     * @param i_UserNiceLog
     * @return
     */
    public Long queryNicesCount(UserNiceLog i_UserNiceLog)
    {
        return this.userNiceLogDAO.queryNicesCount(i_UserNiceLog);
    }
    
}
