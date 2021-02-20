package org.hy.microservice.post;

import java.util.List;

import org.hy.common.xml.annotation.XType;
import org.hy.common.xml.annotation.Xjava;
import org.hy.common.xml.annotation.Xsql;





/**
 * 帖子的附加文件DAO
 *
 * @author      ZhengWei(HY)
 * @createDate  2021-02-20
 * @version     v1.0
 */
@Xjava(id="IPostFileDAO" ,value=XType.XSQL)
public interface IPostFileDAO
{
    
    /**
     * 获取可用于显示的所有帖子附加文件列表
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-02-20
     * @version     v1.0
     *
     * @param i_PostFile
     * @return
     */
    @Xsql("XSQL_PostFile_Query")
    public List<PostFile> queryPosts(PostFile i_PostFile);
    
    

    /**
     * 根据帖子附加文件ID更新
     *
     * @author      ZhengWei(HY)
     * @createDate  2021-02-20
     * @version     v1.0
     *
     * @param i_PostFile
     * @return
     */
    @Xsql("XSQL_PostFile_Update_ByID")
    public int updatePostFile(PostFile i_PostFile);
    
    
    
    /**
     * 根据帖子附加文件ID删除
     *
     * @author      ZhengWei(HY)
     * @createDate  2021-02-20
     * @version     v1.0
     *
     * @param i_PostFile
     * @return
     */
    @Xsql("XSQL_PostFile_Del")
    public int delPostFile(PostFile i_PostFile);
    
}
