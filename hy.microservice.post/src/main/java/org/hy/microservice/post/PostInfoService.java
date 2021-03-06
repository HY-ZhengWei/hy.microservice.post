package org.hy.microservice.post;

import java.util.List;

import org.hy.common.Date;
import org.hy.common.Help;
import org.hy.common.StringHelp;
import org.hy.common.xml.annotation.Xjava;
import org.hy.common.xml.plugins.XSQLGroupResult;





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
    
    @Xjava
    private IPostFileDAO postFileDAO;
    
    
    
    /**
     * 获取可用于显示的所有帖子列表
     * 
     * 当只返回一个帖子时，自动添加附加文件列表的查询
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
        List<PostInfo> v_Ret = this.postInfoDAO.queryPosts(i_PostInfo);
        
        if ( !Help.isNull(v_Ret) && v_Ret.size() == 1 )
        {
            PostFile v_PFile = new PostFile();
            
            v_PFile.setPostID(v_Ret.get(0).getId());
            
            v_Ret.get(0).setFiles(this.postFileDAO.queryPosts(v_PFile));
        }
        
        return v_Ret;
    }
    
    
    
    /**
     * 获取可用于显示的所有帖子列表的总行数
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-01-29
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    public Long queryPostsCount(PostInfo i_PostInfo)
    {
        return this.postInfoDAO.queryPostsCount(i_PostInfo);
    }
    
    
    
    /**
     * 查询关于我的统计数据。如，我的点赞数、评论数量、收藏数量、发帖数量等
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-01-30
     * @version     v1.0
     *
     * @param i_UserID
     * @return
     */
    public PostInfo queryMyCount(String i_UserID)
    {
        return this.postInfoDAO.queryMyCount(i_UserID);
    }
    
    
    
    /**
     * 针对某一业务类型，查询的统计数据。如，我的点赞数、评论数量、收藏数量、发帖数量等
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-03-06
     * @version     v1.0
     *
     * @param i_ServiceType
     * @return
     */
    public PostInfo queryServiceTypeCount(String i_ServiceType)
    {
        return this.postInfoDAO.queryServiceTypeCount(i_ServiceType);
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
        io_PostInfo.setOrderBy(0);
        io_PostInfo.setAuditState("1");
        io_PostInfo.setAuditResult("1");
        io_PostInfo.setExpireTime(new Date("9999-12-31 23:59:59"));
        
        if ( !Help.isNull(io_PostInfo.getFiles()) )
        {
            for (PostFile v_PFile : io_PostInfo.getFiles())
            {
                v_PFile.setId(StringHelp.getUUID());
                v_PFile.setServiceType(io_PostInfo.getServiceType());
                v_PFile.setPostTypeID( io_PostInfo.getPostTypeID());
                v_PFile.setPostID(     io_PostInfo.getId());
                v_PFile.setIsShow(1);
                v_PFile.setOrderBy(1);
            }
        }
        
        XSQLGroupResult v_GXSQLRet = this.postInfoDAO.addPost(io_PostInfo);
        return v_GXSQLRet.isSuccess();
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
