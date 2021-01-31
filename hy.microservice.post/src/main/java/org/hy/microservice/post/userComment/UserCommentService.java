package org.hy.microservice.post.userComment;

import java.util.List;

import org.hy.common.Help;
import org.hy.common.StringHelp;
import org.hy.common.xml.annotation.Xjava;





/**
 * 用户评论的业务层
 *
 * @author      ZhengWei(HY)
 * @createDate  2021-01-31
 * @version     v1.0
 */
@Xjava
public class UserCommentService
{
    
    @Xjava
    private IUserCommentDAO userCommentDAO;
    
    
    
    /**
     * 查询用户评论列表
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-01-31
     * @version     v1.0
     *
     * @param i_UserComment
     * @return
     */
    public List<UserComment> queryComments(UserComment i_UserComment)
    {
        return this.userCommentDAO.queryComments(i_UserComment);
    }
    
    
    
    /**
     * 获取可用于显示的所有评论列表的总行数
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-01-29
     * @version     v1.0
     *
     * @param i_UserComment
     * @return
     */
    public Long queryCommentsCount(UserComment i_UserComment)
    {
        return this.userCommentDAO.queryCommentsCount(i_UserComment);
    }
    
    
    
    /**
     * 查询用户评论的所有回复列表
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-01-31
     * @version     v1.0
     *
     * @param i_UserComment
     * @return
     */
    public List<UserComment> queryCommentsAllReply(UserComment i_UserComment)
    {
        return this.userCommentDAO.queryCommentsAllReply(i_UserComment);
    }
    
    
    
    /**
     * 添加用户评论
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-01-31
     * @version     v1.0
     *
     * @param io_UserComment
     * @return
     */
    public boolean addComment(UserComment io_UserComment)
    {
        io_UserComment.setId(StringHelp.getUUID());
        io_UserComment.setServiceType(Help.NVL(io_UserComment.getServiceType() ,"-"));
        io_UserComment.setGoodCount(0L);
        
        return this.userCommentDAO.addComment(io_UserComment) >= 1;
    }
    
    
    
    /**
     * 删除用户评论
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-01-31
     * @version     v1.0
     *
     * @param i_UserComment
     * @return
     */
    public boolean delComment(UserComment i_UserComment)
    {
        return this.userCommentDAO.delComment(i_UserComment) >= 1;
    }
    
    
    
    /**
     * 点赞（加）
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-01-31
     * @version     v1.0
     *
     * @param i_UserComment
     * @return
     */
    public boolean goodCountAdd(UserComment i_UserComment)
    {
        return this.userCommentDAO.goodCountAdd(i_UserComment) >= 1;
    }
    
    
    
    /**
     * 点赞（减）
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-01-31
     * @version     v1.0
     *
     * @param i_UserComment
     * @return
     */
    public boolean goodCountSubtract(UserComment i_UserComment)
    {
        return this.userCommentDAO.goodCountSubtract(i_UserComment) >= 1;
    }
    
}
