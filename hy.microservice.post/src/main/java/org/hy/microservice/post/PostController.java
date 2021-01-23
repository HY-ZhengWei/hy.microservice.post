package org.hy.microservice.post;

import java.util.List;

import org.hy.common.xml.log.Logger;
import org.hy.microservice.post.common.BaseResponse;
import org.hy.microservice.post.userFavorites.UserFavoritesLog;
import org.hy.microservice.post.userFavorites.UserFavoritesLogService;
import org.hy.microservice.post.userNice.UserNiceLog;
import org.hy.microservice.post.userNice.UserNiceLogService;
import org.hy.microservice.post.userOpen.UserOpenLog;
import org.hy.microservice.post.userOpen.UserOpenLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;





/**
 * 发帖子的控制层
 *
 * @author      ZhengWei(HY)
 * @createDate  2020-10-19
 * @version     v1.0
 */
@Controller
@RequestMapping("post")
public class PostController
{
    
    private static final Logger $Logger = new Logger(PostController.class);
    
    @Autowired
    @Qualifier("PostInfoService")
    private PostInfoService postService;
    
    @Autowired
    @Qualifier("UserOpenLogService")
    public UserOpenLogService userOpenLogService;
    
    @Autowired
    @Qualifier("UserNiceLogService")
    public UserNiceLogService userNiceLogService;
    
    @Autowired
    @Qualifier("UserFavoritesLogService")
    public UserFavoritesLogService userFavoritesLogService;
    
    
    
    /**
     * 用户发帖子
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-19
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    @RequestMapping(value="send" ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> send(PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_Ret = new BaseResponse<PostInfo>();
        
        boolean v_AddRet = this.postService.addPost(i_PostInfo);
        if ( v_AddRet )
        {
            $Logger.info("用户（" + i_PostInfo.getUserName() + i_PostInfo.getUserID() + "）发贴" + i_PostInfo.getTitle() + "，成功");
            return v_Ret.setData(i_PostInfo);
        }
        else
        {
            $Logger.error("用户（" + i_PostInfo.getUserName() + i_PostInfo.getUserID() + "）发贴" + i_PostInfo.getTitle() + "，异常");
            return v_Ret.setCode("-999").setMessage("系统异常");
        }
    }
    
    
    
    /**
     * 获取用户发帖子的列表 
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-20
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    @RequestMapping(value="posts" ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> queryPosts(PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_Ret = new BaseResponse<PostInfo>();
        
        v_Ret.setData(this.postService.queryPosts(i_PostInfo));
        
        return v_Ret;
    }
    
    
    
    /**
     * 点赞（加）
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-21
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    @RequestMapping(value="goodCountAdd" ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> goodCountAdd(PostInfo i_PostInfo)
    {
        if ( this.postService.goodCountAdd(i_PostInfo) )
        {
            UserNiceLog v_Log = new UserNiceLog();
            
            v_Log.setNiceID(  i_PostInfo.getId());
            v_Log.setNiceType(i_PostInfo.getServiceType());
            v_Log.setUserID(  i_PostInfo.getUserID());
            
            if ( !this.userNiceLogService.goodCountAdd(v_Log) )
            {
                this.postService.goodCountSubtract(i_PostInfo);
            }
        }
        
        return this.queryPosts(i_PostInfo);
    }
    
    
    
    /**
     * 点赞（减）
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-21
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    @RequestMapping(value="goodCountSubtract" ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> goodCountSubtract(PostInfo i_PostInfo)
    {
        if ( this.postService.goodCountSubtract(i_PostInfo) )
        {
            UserNiceLog v_Log = new UserNiceLog();
            
            v_Log.setNiceID(  i_PostInfo.getId());
            v_Log.setNiceType(i_PostInfo.getServiceType());
            v_Log.setUserID(  i_PostInfo.getUserID());
            
            if ( !this.userNiceLogService.goodCountSubtract(v_Log) )
            {
                this.postService.goodCountAdd(i_PostInfo);
            }
        }
        
        return this.queryPosts(i_PostInfo);
    }
    
    
    
    /**
     * 收藏（加）
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-21
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    @RequestMapping(value="favoritesCountAdd" ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> favoritesCountAdd(PostInfo i_PostInfo)
    {
        if ( this.postService.favoritesCountAdd(i_PostInfo) )
        {
            UserFavoritesLog v_Log = new UserFavoritesLog();
            
            v_Log.setFavoritesID(i_PostInfo.getId());
            v_Log.setServiceType(i_PostInfo.getServiceType());
            v_Log.setUserID(     i_PostInfo.getUserID());
            
            if ( !this.userFavoritesLogService.addLog(v_Log) )
            {
                this.postService.favoritesCountSubtract(i_PostInfo);
            }
        }
        
        return this.queryPosts(i_PostInfo);
    }
    
    
    
    /**
     * 收藏（减）
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-21
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    @RequestMapping(value="favoritesCountSubtract" ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> favoritesCountSubtract(PostInfo i_PostInfo)
    {
        if ( this.postService.favoritesCountSubtract(i_PostInfo) )
        {
            UserFavoritesLog v_Log = new UserFavoritesLog();
            
            v_Log.setFavoritesID(i_PostInfo.getId());
            v_Log.setServiceType(i_PostInfo.getServiceType());
            v_Log.setUserID(     i_PostInfo.getUserID());
            
            if ( !this.userFavoritesLogService.delLog(v_Log) )
            {
                this.postService.favoritesCountAdd(i_PostInfo);
            }
        }
        
        return this.queryPosts(i_PostInfo);
    }
    
    
    
    /**
     * 阅读（加）
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-10-21
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    @RequestMapping(value="openCountAdd" ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> openCountAdd(PostInfo i_PostInfo)
    {
        if ( this.postService.openCountAdd(i_PostInfo) )
        {
            UserOpenLog v_Log = new UserOpenLog();
            
            v_Log.setOpenID(     i_PostInfo.getId());
            v_Log.setServiceType(i_PostInfo.getServiceType());
            v_Log.setUserID(     i_PostInfo.getUserID());
            
            this.userOpenLogService.addLog(v_Log);  // 不做异常时，阅读减的操作
        }
        
        return this.queryPosts(i_PostInfo);
    }



    /**
     * 获取用户发帖子详情
     *
     * @author      LiHao
     * @createDate  2020-10-20
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    @RequestMapping(value="postsCount" ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> queryPostsCount(PostInfo i_PostInfo)
    {
        List<PostInfo> v_Ret = this.postService.queryPosts(i_PostInfo);

        PostInfo postInfo = new PostInfo();
        if (v_Ret.size() > 0)
        {
            int commentCount = 0;                // 我的回复数量
            int favoritesCount = 0;              // 我的收藏数量
            int niceCount = 0;                   // 我的点赞数量
            for (PostInfo postInfo1 : v_Ret)
            {
                commentCount   +=  postInfo1.getMyIsComment();
                favoritesCount +=  postInfo1.getFavoritesCount();
                niceCount      +=  postInfo1.getMyIsNice();
            }

            postInfo.setMyIsNice(niceCount);
            postInfo.setMyIsComment(commentCount);
            postInfo.setMyIsFavorites(favoritesCount);
            postInfo.setPostCount(v_Ret.size());
        }

        return new BaseResponse<PostInfo>().setData(postInfo);
    }

    
    
    /**
     * 查询用户收藏的发帖子列表
     *
     * @author      Lihao
     * @createDate  2020-11-7
     * @version     v1.0
     *
     * @param i_UserFavoritesLog
     * @return
     */
    @RequestMapping(value="favoritesPosts" ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> queryFavoritesPosts(UserFavoritesLog i_UserFavoritesLog)
    {
        List<PostInfo> v_Ret = null;

        v_Ret = this.userFavoritesLogService.queryFavoritesLog(i_UserFavoritesLog);

        return new BaseResponse<PostInfo>().setData(v_Ret);
    }


    
    /**
     * 查询用户点赞的发帖子列表
     *
     * @author      Lihao
     * @createDate  2020-11-7
     * @version     v1.0
     *
     * @param i_UserNiceLog
     * @return
     */
    @RequestMapping(value="nicePosts" ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<PostInfo> queryNicePosts(UserNiceLog i_UserNiceLog)
    {
        List<PostInfo> v_Ret = null;

        v_Ret = this.userNiceLogService.queryUserNiceLog(i_UserNiceLog);

        return v_Ret;
    }
}
