package org.hy.microservice.post;

import java.util.List;

import org.hy.common.xml.annotation.XType;
import org.hy.common.xml.annotation.Xjava;
import org.hy.common.xml.annotation.Xsql;





/**
 * 发帖子的DAO
 *
 * @author      ZhengWei(HY)
 * @createDate  2020-10-19
 * @version     v1.0
 */
@Xjava(id="IPostInfoDAO" ,value=XType.XSQL)
public interface IPostInfoDAO
{
    
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
    @Xsql("XSQL_PostInfo_Query")
    public List<PostInfo> queryPosts(PostInfo i_PostInfo);
    
    
    
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
    @Xsql("XSQL_PostInfo_Query_Count")
    public Long queryPostsCount(PostInfo i_PostInfo);

    

    /**
     * 添加发帖子
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-19
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    @Xsql("XSQL_PostInfo_Insert")
    public int addPost(PostInfo i_PostInfo);
    
    
    
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
    @Xsql("XSQL_PostInfo_Update_OpenCount_Add")
    public int openCountAdd(PostInfo i_PostInfo);
    
    
    
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
    @Xsql("XSQL_PostInfo_Update_MessageCount_Add")
    public int messageCountAdd(PostInfo i_PostInfo);
    
    
    
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
    @Xsql("XSQL_PostInfo_Update_GoodCount_Add")
    public int goodCountAdd(PostInfo i_PostInfo);
    
    
    
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
    @Xsql("XSQL_PostInfo_Update_GoodCount_Subtract")
    public int goodCountSubtract(PostInfo i_PostInfo);
    
    
    
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
    @Xsql("XSQL_PostInfo_Update_FavoritesCount_Add")
    public int favoritesCountAdd(PostInfo i_PostInfo);
    
    
    
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
    @Xsql("XSQL_PostInfo_Update_FavoritesCount_Subtract")
    public int favoritesCountSubtract(PostInfo i_PostInfo);

    
    
    /**
     * 根据帖子ID更新帖子详情
     *
     * @author      LiHao
     * @createDate  2020-11-29
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    @Xsql("XSQL_PostInfo_Update_PostByID")
    public int updatePost(PostInfo i_PostInfo);
}
