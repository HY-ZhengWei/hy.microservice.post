package org.hy.microservice.post.userNice;

import java.util.List;

import org.hy.common.xml.annotation.XType;
import org.hy.common.xml.annotation.Xjava;
import org.hy.common.xml.annotation.Xsql;





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
    @Xsql("XSQL_UserNiceLog_Insert")
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
    @Xsql("XSQL_UserNiceLog_Del")
    public int delLog(UserNiceLog i_UserNiceLog);

    

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
    @Xsql("XSQL_UserNiceLog_Query_NiceCount")
    public Long queryNiceCount(UserNiceLog i_UserNiceLog);
    
    
    
    /**
     * 点赞列表查询
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-02-08
     * @version     v1.0
     *
     * @param i_UserNiceLog
     * @return
     */
    @Xsql("XSQL_UserNiceLog_Query")
    public List<UserNiceLog> queryNices(UserNiceLog i_UserNiceLog);
    
    
    
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
    @Xsql("XSQL_UserNiceLog_Query_Count")
    public Long queryNicesCount(UserNiceLog i_UserNiceLog);
    
}