package org.hy.microservice.post;

import java.util.List;

import org.hy.common.Help;
import org.hy.common.StringHelp;
import org.hy.common.xml.annotation.Xjava;





/**
 * 发帖子的业务类
 *
 * @author      ZhengWei(HY)
 * @createDate  2020-10-19
 * @version     v1.0
 */
@Xjava
public class PostInfoService
{
    
    @Xjava
    private IPostInfoDAO postInfoDAO;
    
    
    
    /**
     * 获取可用于显示的所有帖子列表
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-19
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    public List<PostInfo> queryPosts(PostInfo i_PostInfo)
    {
        return this.postInfoDAO.queryPosts(i_PostInfo);
    }

    
    
    /**
     * 添加发帖子
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-19
     * @version     v1.0
     *
     * @param io_PostInfo
     * @return
     */
    public boolean addPost(PostInfo io_PostInfo)
    {
        io_PostInfo.setId(StringHelp.getUUID());
        io_PostInfo.setServiceType(Help.NVL(io_PostInfo.getServiceType() ,"-"));
        io_PostInfo.setOpenCount(0L);
        io_PostInfo.setMessageCount(0L);
        io_PostInfo.setFavoritesCount(0L);
        io_PostInfo.setGoodCount(0L);
        io_PostInfo.setIsRecommend(0);
        io_PostInfo.setIsShow(1);
        io_PostInfo.setAuditState("1");
        
        return this.postInfoDAO.addPost(io_PostInfo) == 1;
    }
    
    
    
    /**
     * 阅读次数（加）
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-21
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    public boolean openCountAdd(PostInfo i_PostInfo)
    {
        return this.postInfoDAO.openCountAdd(i_PostInfo) == 1;
    }
    
    
    
    /**
     * 评论、留言次数（加）
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-19
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    public boolean messageCountAdd(PostInfo i_PostInfo)
    {
        return this.postInfoDAO.messageCountAdd(i_PostInfo) == 1;
    }
    
    
    
    /**
     * 点赞（加）
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-19
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    public boolean goodCountAdd(PostInfo i_PostInfo)
    {
        return this.postInfoDAO.goodCountAdd(i_PostInfo) == 1;
    }
    
    
    
    /**
     * 点赞（减）
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-19
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    public boolean goodCountSubtract(PostInfo i_PostInfo)
    {
        return this.postInfoDAO.goodCountSubtract(i_PostInfo) == 1;
    }
    
    
    
    /**
     * 收藏（加）
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-19
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    public boolean favoritesCountAdd(PostInfo i_PostInfo)
    {
        return this.postInfoDAO.favoritesCountAdd(i_PostInfo) == 1;
    }
    
    
    
    /**
     * 收藏（减）
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-19
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    public boolean favoritesCountSubtract(PostInfo i_PostInfo)
    {
        return this.postInfoDAO.favoritesCountSubtract(i_PostInfo) == 1;
    }

    
    
    /**
     * @Title:updatePost
     * @Description:  更新帖子
     *
     * @author LHao
     * @date 2020/11/29  10:43
     * @version [v1.0]
     *
     * @Param [i_PostInfo]
     *
     * @return boolean
     */
    public boolean updatePost(PostInfo i_PostInfo)
    {
        return this.postInfoDAO.updatePost(i_PostInfo) == 1;
    }
    
}
