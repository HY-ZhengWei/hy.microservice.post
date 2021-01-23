package org.hy.microservice.post.userNice;

import org.hy.common.xml.annotation.XType;
import org.hy.common.xml.annotation.Xjava;
import org.hy.common.xml.annotation.Xsql;
import org.hy.microservice.post.PostInfo;

import java.util.List;





/**
 * 点赞信息的DAO
 *
 * @author      ZhengWei(HY)
 * @createDate  2020-09-07
 * @version     v1.0
 */
@Xjava(id="UserNiceLogDAO" ,value=XType.XSQL)
public interface IUserNiceLogDAO
{
    
    /**
     * 添加点赞
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-09-07
     * @version     v1.0
     *
     * @param i_UserNiceLog
     * @return
     */
    @Xsql("XSQL_PingShan_UserNiceLog_Insert")
    public int addLog(UserNiceLog i_UserNiceLog);
    
    
    
    /**
     * 取消点赞
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-09-07
     * @version     v1.0
     *
     * @param i_UserNiceLog
     * @return
     */
    @Xsql("XSQL_PingShan_UserNiceLog_Del")
    public int delLog(UserNiceLog i_UserNiceLog);

    

    /**
     * 查询用户收藏
     *
     * @author      Lihao
     * @createDate  2020-10-7
     * @version     v1.0
     *
     * @param i_UserNiceLog
     * @return List<PostInfo>
     */
    @Xsql("XSQL_UserNiceLog_queryList")
    public List<PostInfo> queryUserNiceLog(UserNiceLog i_UserNiceLog);
    
}