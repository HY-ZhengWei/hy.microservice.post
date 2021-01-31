package org.hy.microservice.post.userComment;

import java.util.List;

import org.hy.common.xml.annotation.XType;
import org.hy.common.xml.annotation.Xjava;
import org.hy.common.xml.annotation.Xsql;





/**
 * 用户评论的DAO
 *
 * @author      ZhengWei(HY)
 * @createDate  2021-01-31
 * @version     v1.0
 */
@Xjava(id="UserCommentDAO" ,value=XType.XSQL)
public interface IUserCommentDAO
{
    
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
    @Xsql("XSQL_UserComment_Query")
    public List<UserComment> queryComments(UserComment i_UserComment);
    
    
    
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
    @Xsql("XSQL_UserComment_Query_Count")
    public Long queryCommentsCount(UserComment i_UserComment);
    
    
    
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
    @Xsql("XSQL_UserComment_Query_AllReply")
    public List<UserComment> queryCommentsAllReply(UserComment i_UserComment);
    
    
    
    /**
     * 添加用户评论
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-01-31
     * @version     v1.0
     *
     * @param i_UserComment
     * @return
     */
    @Xsql("XSQL_UserComment_Save")
    public int addComment(UserComment i_UserComment);
    
    
    
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
    @Xsql("XSQL_UserComment_Delete")
    public int delComment(UserComment i_UserComment);
    
    
    
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
    @Xsql("XSQL_UserComment_Update_GoodCount_Add")
    public int goodCountAdd(UserComment i_UserComment);
    
    
    
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
    @Xsql("XSQL_UserComment_Update_GoodCount_Subtract")
    public int goodCountSubtract(UserComment i_UserComment);
    
}
