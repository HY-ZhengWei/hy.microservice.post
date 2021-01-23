package org.hy.microservice.post.userNice;

import org.hy.common.xml.annotation.Xjava;
import org.hy.microservice.post.PostInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;





/**
 * @author LHao
 * @version [v1.0]
 * @ClassName: UserNiceLogService
 * @Description: 用户点赞Service 类
 * @date 2020/10/15 21:13
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service("userNiceLogService")
@Xjava
public class UserNiceLogService 
{

    @Autowired
    @Qualifier("UserNiceLogDAO")
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
     * 查询点赞帖子
     *
     * @author      LiHao
     * @createDate  2020-11-07
     * @version     v1.0
     *
     * @param i_UserNiceLog
     * @return
     */
    public List<PostInfo> queryUserNiceLog(UserNiceLog i_UserNiceLog)
    {
        return this.userNiceLogDAO.queryUserNiceLog(i_UserNiceLog);
    }
    
}
