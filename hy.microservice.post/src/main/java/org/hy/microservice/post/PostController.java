package org.hy.microservice.post;

import java.util.List;

import org.hy.common.Help;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping(value="send" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> send(@RequestBody PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_Ret = new BaseResponse<PostInfo>();
        
        if ( i_PostInfo == null )
        {
            return v_Ret.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_PostInfo.getTitle()) ) 
        {
            return v_Ret.setCode("-2").setMessage("帖子标题为空");
        }
        
        if ( Help.isNull(i_PostInfo.getTitle()) ) 
        {
            return v_Ret.setCode("-3").setMessage("发帖人员编码为空");
        }
        
        if ( Help.isNull(i_PostInfo.getContent()) ) 
        {
            return v_Ret.setCode("-4").setMessage("发帖内容为空");
        }
        
        boolean v_AddRet = this.postService.addPost(i_PostInfo);
        if ( v_AddRet )
        {
            $Logger.info("用户（" + i_PostInfo.getUserName() + i_PostInfo.getUserID() + "）发贴" + i_PostInfo.getTitle() + "，成功");
            return this.queryPosts(i_PostInfo);
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
    @RequestMapping(value="list" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> queryPosts(@RequestBody PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_Ret = new BaseResponse<PostInfo>();
        
        v_Ret.setData(this.postService.queryPosts(i_PostInfo));
        v_Ret.getData().setRecordCount(this.postService.queryPostsCount(i_PostInfo));
        v_Ret.getData().calcPage(i_PostInfo);
        
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
    @RequestMapping(value="goodCountAdd" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> goodCountAdd(@RequestBody PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_RetResp = new BaseResponse<PostInfo>();
        
        if ( i_PostInfo == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_PostInfo.getUserID()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("用户编号为空");
        }
        
        if ( Help.isNull(i_PostInfo.getServiceType()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("业务类型为空");
        }
        
        UserNiceLog v_Log = new UserNiceLog();
        
        v_Log.setNiceID(  i_PostInfo.getId());
        v_Log.setNiceType(i_PostInfo.getServiceType());
        v_Log.setUserID(  i_PostInfo.getUserID());
        
        try
        {
            if ( this.userNiceLogService.goodCountAdd(v_Log) )
            {
                this.postService.goodCountAdd(i_PostInfo);
                
                v_RetResp = this.queryPosts(i_PostInfo);
                return v_RetResp;
            }
        }
        catch (Exception exce)
        {
            $Logger.error("不能重复点赞" ,exce);
        }
        
        return v_RetResp.setCode("-999").setMessage("异常");
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
    @RequestMapping(value="goodCountSubtract" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> goodCountSubtract(@RequestBody PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_RetResp = new BaseResponse<PostInfo>();
        
        if ( i_PostInfo == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_PostInfo.getUserID()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("用户编号为空");
        }
        
        if ( Help.isNull(i_PostInfo.getServiceType()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("业务类型为空");
        }
        
        UserNiceLog v_Log = new UserNiceLog();
        
        v_Log.setNiceID(  i_PostInfo.getId());
        v_Log.setNiceType(i_PostInfo.getServiceType());
        v_Log.setUserID(  i_PostInfo.getUserID());
        
        try
        {
            if ( this.userNiceLogService.goodCountSubtract(v_Log) )
            {
                this.postService.goodCountSubtract(i_PostInfo);
                
                v_RetResp = this.queryPosts(i_PostInfo);
                return v_RetResp;
            }
        }
        catch (Exception exce)
        {
            $Logger.error("不能重复点赞" ,exce);
        }
    
        return v_RetResp.setCode("-999").setMessage("异常");
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
    @RequestMapping(value="favoritesCountAdd" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> favoritesCountAdd(@RequestBody PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_RetResp = new BaseResponse<PostInfo>();
        
        if ( i_PostInfo == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_PostInfo.getUserID()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("用户编号为空");
        }
        
        if ( Help.isNull(i_PostInfo.getServiceType()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("业务类型为空");
        }
        
        UserFavoritesLog v_Log = new UserFavoritesLog();
        
        v_Log.setFavoritesID(i_PostInfo.getId());
        v_Log.setServiceType(i_PostInfo.getServiceType());
        v_Log.setUserID(     i_PostInfo.getUserID());
        
        try
        {
            if ( this.userFavoritesLogService.addLog(v_Log) )
            {
                this.postService.favoritesCountAdd(i_PostInfo);
                v_RetResp = this.queryPosts(i_PostInfo);
                return v_RetResp;
            }
        }
        catch (Exception exce)
        {
            $Logger.error("不能重复收藏" ,exce);
        }
        
        return v_RetResp.setCode("-999").setMessage("异常");
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
    @RequestMapping(value="favoritesCountSubtract" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> favoritesCountSubtract(@RequestBody PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_RetResp = new BaseResponse<PostInfo>();
        
        if ( i_PostInfo == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_PostInfo.getUserID()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("用户编号为空");
        }
        
        if ( Help.isNull(i_PostInfo.getServiceType()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("业务类型为空");
        }
        
        UserFavoritesLog v_Log = new UserFavoritesLog();
        
        v_Log.setFavoritesID(i_PostInfo.getId());
        v_Log.setServiceType(i_PostInfo.getServiceType());
        v_Log.setUserID(     i_PostInfo.getUserID());
        
        try
        {
            if ( this.userFavoritesLogService.delLog(v_Log) )
            {
                this.postService.favoritesCountSubtract(i_PostInfo);
                
                v_RetResp = this.queryPosts(i_PostInfo);
                return v_RetResp;
            }
        }
        catch (Exception exce)
        {
            $Logger.error("不能重复收藏" ,exce);
        }
        
        return v_RetResp.setCode("-999").setMessage("异常");
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
    @RequestMapping(value="openCountAdd" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> openCountAdd(@RequestBody PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_RetResp = new BaseResponse<PostInfo>();
        
        if ( i_PostInfo == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_PostInfo.getUserID()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("用户编号为空");
        }
        
        if ( Help.isNull(i_PostInfo.getServiceType()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("业务类型为空");
        }
        
        UserOpenLog v_Log = new UserOpenLog();
        
        v_Log.setOpenID(     i_PostInfo.getId());
        v_Log.setServiceType(i_PostInfo.getServiceType());
        v_Log.setUserID(     i_PostInfo.getUserID());
        
        try
        {
            if ( this.userOpenLogService.addLog(v_Log) )
            {
                this.postService.openCountAdd(i_PostInfo);
                
                v_RetResp = this.queryPosts(i_PostInfo);
                return v_RetResp;
            }
        }
        catch (Exception exce)
        {
            $Logger.error("不能重复已阅" ,exce);
        }
        
        return v_RetResp.setCode("-999").setMessage("异常");
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
    @RequestMapping(value="postsCount" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> queryPostsCount(@RequestBody PostInfo i_PostInfo)
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
    @RequestMapping(value="favoritesPosts" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> queryFavoritesPosts(@RequestBody UserFavoritesLog i_UserFavoritesLog)
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
    @RequestMapping(value="nicePosts" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<PostInfo> queryNicePosts(@RequestBody UserNiceLog i_UserNiceLog)
    {
        List<PostInfo> v_Ret = null;

        v_Ret = this.userNiceLogService.queryUserNiceLog(i_UserNiceLog);

        return v_Ret;
    }
    
}
