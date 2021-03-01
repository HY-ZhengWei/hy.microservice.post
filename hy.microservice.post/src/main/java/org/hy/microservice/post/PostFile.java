package org.hy.microservice.post;

import org.hy.microservice.common.BaseViewMode;





/**
 * 帖子附加文件 
 *
 * @author      ZhengWei(HY)
 * @createDate  2021-02-20
 * @version     v1.0
 */
public class PostFile extends BaseViewMode
{

    private static final long serialVersionUID = -2591313500785674948L;
    
    /** 主键 */
    private String id;
    
    /** 帖子分类编号 */
    private String postTypeID;
    
    /** 帖子编号 */
    private String postID;
    
    /** 帖子附加文件地址 */
    private String fileUrl;
    
    /** 帖子附加文件ID */
    private String fileUrlID;
    
    /** 帖子附加文件类型 */
    private String fileType;

    
    
    /**
     * 获取：主键
     */
    public String getId()
    {
        return id;
    }

    
    /**
     * 获取：帖子分类编号
     */
    public String getPostTypeID()
    {
        return postTypeID;
    }

    
    /**
     * 获取：帖子编号
     */
    public String getPostID()
    {
        return postID;
    }

    
    /**
     * 获取：帖子附加文件地址
     */
    public String getFileUrl()
    {
        return fileUrl;
    }

    
    /**
     * 获取：帖子附加文件ID
     */
    public String getFileUrlID()
    {
        return fileUrlID;
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
     * 设置：帖子分类编号
     * 
     * @param postTypeID 
     */
    public void setPostTypeID(String postTypeID)
    {
        this.postTypeID = postTypeID;
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
     * 设置：帖子附加文件地址
     * 
     * @param fileUrl 
     */
    public void setFileUrl(String fileUrl)
    {
        this.fileUrl = fileUrl;
    }

    
    /**
     * 设置：帖子附加文件ID
     * 
     * @param fileUrlID 
     */
    public void setFileUrlID(String fileUrlID)
    {
        this.fileUrlID = fileUrlID;
    }

    
    /**
     * 获取：帖子附加文件类型
     */
    public String getFileType()
    {
        return fileType;
    }

    
    /**
     * 设置：帖子附加文件类型
     * 
     * @param fileType 
     */
    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }
    
}
