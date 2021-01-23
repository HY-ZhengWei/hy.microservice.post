package org.hy.microservice.post.userOpen;

import org.hy.common.xml.annotation.XType;
import org.hy.common.xml.annotation.Xjava;
import org.hy.common.xml.annotation.Xsql;





/**
 * 用户阅读信息的DAO
 *
 * @author      ZhengWei(HY)
 * @createDate  2020-10-21
 * @version     v1.0
 */
@Xjava(id="UserOpenLogDAO" ,value=XType.XSQL)
public interface IUserOpenLogDAO
{
    
    /**
     * 添加用户收藏
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-19
     * @version     v1.0
     *
     * @param i_UserOpenLog
     * @return
     */
    @Xsql("XSQL_UserOpenLog_Insert")
    public int addLog(UserOpenLog i_UserOpenLog);
    
}