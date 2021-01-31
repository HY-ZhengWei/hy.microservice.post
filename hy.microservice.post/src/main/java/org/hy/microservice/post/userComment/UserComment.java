package org.hy.microservice.post.userComment;

import java.util.List;

import org.hy.common.TreeObjectNode;
import org.hy.microservice.post.common.BaseViewMode;





/**
 * 用户的评论信息
 *
 * @author      LHao
 * @createDate  2020-10-15
 * @version     v1.0
 */
public class UserComment extends BaseViewMode implements TreeObjectNode<UserComment>
{
    
    private static final long serialVersionUID = -3383751646605165920L;
    
    /** 主键 */
    private String  id;
    
    /** 父评论编号，针对用户评论的评论 */
    private String  parentID;
    
    /** 帖子编号 */
    private String  postID;     
    
    /** 评论内容 */
    private String  comment;  
    
    /** 评分 */
    private Integer score;   
    
    /** 点赞次数 */
    private Long    goodCount;
    
    /** 评论的回复 */
    private List<UserComment> replys;
    
    
    
    /**
     * 获取：主键
     */
    public String getId()
    {
        return id;
    }

    
    /**
     * 获取：父评论编号，针对用户评论的评论
     */
    public String getParentID()
    {
        return parentID;
    }

    
    /**
     * 获取：帖子编号
     */
    public String getPostID()
    {
        return postID;
    }

    
    /**
     * 获取：评论内容
     */
    public String getComment()
    {
        return comment;
    }

    
    /**
     * 获取：评分
     */
    public Integer getScore()
    {
        return score;
    }

    
    /**
     * 获取：点赞次数
     */
    public Long getGoodCount()
    {
        return goodCount;
    }

    
    /**
     * 获取：评论的回复
     */
    public List<UserComment> getReplys()
    {
        return replys;
    }

    
    /**
     * 设置：主键
     * 
     * @param id 
     */
    public void setId(String id)
    {
        this.id = id;
    }

    
    /**
     * 设置：父评论编号，针对用户评论的评论
     * 
     * @param parentID 
     */
    public void setParentID(String parentID)
    {
        this.parentID = parentID;
    }

    
    /**
     * 设置：帖子编号
     * 
     * @param postID 
     */
    public void setPostID(String postID)
    {
        this.postID = postID;
    }

    
    /**
     * 设置：评论内容
     * 
     * @param comment 
     */
    public void setComment(String comment)
    {
        this.comment = comment;
    }

    
    /**
     * 设置：评分
     * 
     * @param score 
     */
    public void setScore(Integer score)
    {
        this.score = score;
    }

    
    /**
     * 设置：点赞次数
     * 
     * @param goodCount 
     */
    public void setGoodCount(Long goodCount)
    {
        this.goodCount = goodCount;
    }

    
    /**
     * 设置：评论的回复
     * 
     * @param replys 
     */
    public void setReplys(List<UserComment> replys)
    {
        this.replys = replys;
    }

    
    
    /**
     * 获取当前节点ID
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-04-21
     * @version     v1.0
     *
     * @return
     */
    public String gatTreeObjectNodeID()
    {
        return this.id;
    }
    
    
    
    /**
     * 获取父节点ID
     * 
     * 此接口可以没有，但为了能将普通的List结构转为一颗树结构，而冗余定义的。
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-04-21
     * @version     v1.0
     *
     * @return
     */
    public String gatTreeObjectSuperID()
    {
        return this.parentID;
    }
    
    
    
    /**
     * 获取当前节点的直属子节点
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-04-21
     * @version     v1.0
     *
     * @return
     */
    public List<UserComment> gatTreeObjectChilds()
    {
        return this.replys;
    }
    
    
    
    /**
     * 添加子节点
     * 
     * 注意：在添加时异常时，请抛出异常，无异常即表示成功。
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-04-21
     * @version     v1.0
     *
     * @param i_ChildNode    子节点数据 
     */
    public void addTreeObjectChild(UserComment i_ChildNode)
    {
        this.replys.add(i_ChildNode);
    }
    
    
    
    /**
     * 删除子节点
     * 
     * 注意：在删除时异常时，请抛出异常，无异常即表示成功。
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-04-21
     * @version     v1.0
     *
     * @param i_ChildNode    子节点数据 
     */
    public void delTreeObjectChild(UserComment i_ChildNode)
    {
        for (int x=this.replys.size() - 1; x >=0; x--)
        {
            UserComment v_Item = this.replys.get(x);
            if ( v_Item.getId().equals(i_ChildNode.getId()) )
            {
                this.replys.remove(x);
                return;
            }
        }
    }
    
}
