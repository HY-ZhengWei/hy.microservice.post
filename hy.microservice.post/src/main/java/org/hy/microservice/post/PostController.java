package org.hy.microservice.post;

import org.hy.common.Help;
import org.hy.common.app.Param;
import org.hy.common.xml.log.Logger;
import org.hy.microservice.common.BaseResponse;
import org.hy.microservice.post.user.UserSSO;
import org.hy.microservice.post.user.UserService;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    private UserOpenLogService userOpenLogService;
    
    @Autowired
    @Qualifier("UserNiceLogService")
    private UserNiceLogService userNiceLogService;
    
    @Autowired
    @Qualifier("UserFavoritesLogService")
    private UserFavoritesLogService userFavoritesLogService;
    
    @Autowired
    @Qualifier("UserService")
    private UserService userService;
    
    @Autowired
    @Qualifier("MS_Post_IsCheckToken")
    private Param isCheckToken;
    
    
    
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
    public BaseResponse<PostInfo> send(@RequestParam("token") String i_Token ,@RequestBody PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_RetResp = new BaseResponse<PostInfo>();
        
        if ( i_PostInfo == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_PostInfo.getTitle()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("帖子标题为空");
        }
        
        if ( Help.isNull(i_PostInfo.getUserID()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("发帖人员编码为空");
        }
        
        if ( Help.isNull(i_PostInfo.getContent()) ) 
        {
            return v_RetResp.setCode("-4").setMessage("发帖内容为空");
        }
        
        
        if ( isCheckToken != null && Boolean.parseBoolean(isCheckToken.getValue()) )
        {
            // 验证票据及用户登录状态
            if ( Help.isNull(i_Token) ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            UserSSO v_User = this.userService.getUser(i_Token);
            if ( v_User == null ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            if ( !v_User.getUserId().equals(i_PostInfo.getUserID()) )
            {
                return v_RetResp.setCode("-902").setMessage("发贴用户与登录用户不一致");
            }
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
            return v_RetResp.setCode("-999").setMessage("系统异常");
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
        BaseResponse<PostInfo> v_RetResp = new BaseResponse<PostInfo>();
        
        v_RetResp.setData(this.postService.queryPosts(i_PostInfo));
        v_RetResp.getData().setRecordCount(this.postService.queryPostsCount(i_PostInfo));
        v_RetResp.getData().calcPage(i_PostInfo);
        
        return v_RetResp;
    }
    
    
    
    /**
     * 获取用户统计信息（点赞量、发帖量、收藏量、评论量）
     *
     * @author      LiHao
     * @createDate  2020-10-20
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    @RequestMapping(value="myCount" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> queryPostsCount(@RequestParam("token") String i_Token ,@RequestBody PostInfo i_PostInfo)
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
        
        
        if ( isCheckToken != null && Boolean.parseBoolean(isCheckToken.getValue()) )
        {
            // 验证票据及用户登录状态
            if ( Help.isNull(i_Token) ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            UserSSO v_User = this.userService.getUser(i_Token);
            if ( v_User == null ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            if ( !v_User.getUserId().equals(i_PostInfo.getUserID()) )
            {
                return v_RetResp.setCode("-902").setMessage("查询用户与登录用户不一致");
            }
        }
        
        
        v_RetResp.setData(this.postService.queryMyCount(i_PostInfo.getUserID()));

        return v_RetResp;
    }
    
    
    
    /**
     * 获取业务类型的统计信息（点赞量、发帖量、收藏量、评论量）
     *
     * @author      ZhengWei(HY)
     * @createDate  2021-03-06
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    @RequestMapping(value="serviceTypeCount" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<PostInfo> queryServiceTypeCount(@RequestParam("token") String i_Token ,@RequestBody PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_RetResp = new BaseResponse<PostInfo>();
        
        if ( i_PostInfo == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_PostInfo.getServiceType()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("业务类型为空");
        }
        
        if ( isCheckToken != null && Boolean.parseBoolean(isCheckToken.getValue()) )
        {
            // 验证票据及用户登录状态
            if ( Help.isNull(i_Token) ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            UserSSO v_User = this.userService.getUser(i_Token);
            if ( v_User == null ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
        }
        
        
        v_RetResp.setData(this.postService.queryServiceTypeCount(i_PostInfo.getServiceType()));

        return v_RetResp;
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
    public BaseResponse<PostInfo> goodCountAdd(@RequestParam("token") String i_Token ,@RequestBody PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_RetResp = new BaseResponse<PostInfo>();
        
        if ( i_PostInfo == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_PostInfo.getId()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("帖子编号为空");
        }
        
        if ( Help.isNull(i_PostInfo.getUserID()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("用户编号为空");
        }
        
        if ( Help.isNull(i_PostInfo.getServiceType()) ) 
        {
            return v_RetResp.setCode("-4").setMessage("业务类型为空");
        }
        
        
        if ( isCheckToken != null && Boolean.parseBoolean(isCheckToken.getValue()) )
        {
            // 验证票据及用户登录状态
            if ( Help.isNull(i_Token) ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            UserSSO v_User = this.userService.getUser(i_Token);
            if ( v_User == null ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            if ( !v_User.getUserId().equals(i_PostInfo.getUserID()) )
            {
                return v_RetResp.setCode("-902").setMessage("发贴用户与登录用户不一致");
            }
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
                
                i_PostInfo.setUserID(null);
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
    public BaseResponse<PostInfo> goodCountSubtract(@RequestParam("token") String i_Token ,@RequestBody PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_RetResp = new BaseResponse<PostInfo>();
        
        if ( i_PostInfo == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_PostInfo.getId()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("帖子编号为空");
        }
        
        if ( Help.isNull(i_PostInfo.getUserID()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("用户编号为空");
        }
        
        if ( Help.isNull(i_PostInfo.getServiceType()) ) 
        {
            return v_RetResp.setCode("-4").setMessage("业务类型为空");
        }
        
        
        if ( isCheckToken != null && Boolean.parseBoolean(isCheckToken.getValue()) )
        {
            // 验证票据及用户登录状态
            if ( Help.isNull(i_Token) ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            UserSSO v_User = this.userService.getUser(i_Token);
            if ( v_User == null ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            if ( !v_User.getUserId().equals(i_PostInfo.getUserID()) )
            {
                return v_RetResp.setCode("-902").setMessage("发贴用户与登录用户不一致");
            }
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
                
                i_PostInfo.setUserID(null);
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
    public BaseResponse<PostInfo> favoritesCountAdd(@RequestParam("token") String i_Token ,@RequestBody PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_RetResp = new BaseResponse<PostInfo>();
        
        if ( i_PostInfo == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_PostInfo.getId()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("帖子编号为空");
        }
        
        if ( Help.isNull(i_PostInfo.getUserID()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("用户编号为空");
        }
        
        if ( Help.isNull(i_PostInfo.getServiceType()) ) 
        {
            return v_RetResp.setCode("-4").setMessage("业务类型为空");
        }
        
        
        if ( isCheckToken != null && Boolean.parseBoolean(isCheckToken.getValue()) )
        {
            // 验证票据及用户登录状态
            if ( Help.isNull(i_Token) ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            UserSSO v_User = this.userService.getUser(i_Token);
            if ( v_User == null ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            if ( !v_User.getUserId().equals(i_PostInfo.getUserID()) )
            {
                return v_RetResp.setCode("-902").setMessage("发贴用户与登录用户不一致");
            }
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
                
                i_PostInfo.setUserID(null);
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
    public BaseResponse<PostInfo> favoritesCountSubtract(@RequestParam("token") String i_Token ,@RequestBody PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_RetResp = new BaseResponse<PostInfo>();
        
        if ( i_PostInfo == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_PostInfo.getId()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("帖子编号为空");
        }
        
        if ( Help.isNull(i_PostInfo.getUserID()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("用户编号为空");
        }
        
        if ( Help.isNull(i_PostInfo.getServiceType()) ) 
        {
            return v_RetResp.setCode("-4").setMessage("业务类型为空");
        }
        
        
        if ( isCheckToken != null && Boolean.parseBoolean(isCheckToken.getValue()) )
        {
            // 验证票据及用户登录状态
            if ( Help.isNull(i_Token) ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            UserSSO v_User = this.userService.getUser(i_Token);
            if ( v_User == null ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            if ( !v_User.getUserId().equals(i_PostInfo.getUserID()) )
            {
                return v_RetResp.setCode("-902").setMessage("发贴用户与登录用户不一致");
            }
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
                
                i_PostInfo.setUserID(null);
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
    public BaseResponse<PostInfo> openCountAdd(@RequestParam("token") String i_Token ,@RequestBody PostInfo i_PostInfo)
    {
        BaseResponse<PostInfo> v_RetResp = new BaseResponse<PostInfo>();
        
        if ( i_PostInfo == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_PostInfo.getId()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("帖子编号为空");
        }
        
        if ( Help.isNull(i_PostInfo.getUserID()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("用户编号为空");
        }
        
        if ( Help.isNull(i_PostInfo.getServiceType()) ) 
        {
            return v_RetResp.setCode("-4").setMessage("业务类型为空");
        }
        
        
        if ( isCheckToken != null && Boolean.parseBoolean(isCheckToken.getValue()) )
        {
            // 验证票据及用户登录状态
            if ( Help.isNull(i_Token) ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            UserSSO v_User = this.userService.getUser(i_Token);
            if ( v_User == null ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            if ( !v_User.getUserId().equals(i_PostInfo.getUserID()) )
            {
                return v_RetResp.setCode("-902").setMessage("发贴用户与登录用户不一致");
            }
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
                
                i_PostInfo.setUserID(null);
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
    
}
