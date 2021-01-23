package org.hy.microservice.post.userOpen;

import org.hy.common.xml.annotation.Xjava;





/**
 * 用户阅读的业务层
 *
 * @author      ZhengWei(HY)
 * @createDate  2020-10-21
 * @version     v1.0
 */
@Xjava
public class UserOpenLogService
{
    
    @Xjava
    private IUserOpenLogDAO userOpenLogDAO;
    
    
    
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
    public boolean addLog(UserOpenLog i_UserOpenLog)
    {
        return this.userOpenLogDAO.addLog(i_UserOpenLog) == 1;
    }

}
