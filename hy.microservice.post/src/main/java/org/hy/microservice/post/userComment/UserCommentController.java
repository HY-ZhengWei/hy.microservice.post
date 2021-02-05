package org.hy.microservice.post.userComment;

import org.hy.common.Help;
import org.hy.common.app.Param;
import org.hy.common.xml.log.Logger;
import org.hy.microservice.common.BaseResponse;
import org.hy.microservice.post.user.UserSSO;
import org.hy.microservice.post.user.UserService;
import org.hy.microservice.post.userNice.UserNiceLog;
import org.hy.microservice.post.userNice.UserNiceLogService;
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
 * 评论的控制层
 *
 * @author      ZhengWei(HY)
 * @createDate  2021-01-31
 * @version     v1.0
 */
@Controller
@RequestMapping("comment")
public class UserCommentController
{
    
    private static final Logger $Logger = new Logger(UserCommentController.class);
    
    @Autowired
    @Qualifier("UserCommentService")
    private UserCommentService commentService;
    
    @Autowired
    @Qualifier("UserNiceLogService")
    public UserNiceLogService userNiceLogService;
    
    @Autowired
    @Qualifier("UserService")
    public UserService userService;
    
    @Autowired
    @Qualifier("MS_Post_IsCheckToken")
    public Param isCheckToken;
    
    
    
    /**
     * 用户发评论
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-01-31
     * @version     v1.0
     *
     * @param i_UserComment
     * @return
     */
    @RequestMapping(value="send" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<UserComment> send(@RequestParam("token") String i_Token ,@RequestBody UserComment i_UserComment)
    {
        BaseResponse<UserComment> v_RetResp = new BaseResponse<UserComment>();
        
        if ( i_UserComment == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_UserComment.getComment()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("评论内容为空");
        }
        
        if ( Help.isNull(i_UserComment.getPostID()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("被评论对象的编码为空");
        }
        
        if ( Help.isNull(i_UserComment.getUserID()) ) 
        {
            return v_RetResp.setCode("-4").setMessage("评论用户编码为空");
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
            
            if ( !v_User.getUserId().equals(i_UserComment.getUserID()) )
            {
                return v_RetResp.setCode("-902").setMessage("评论用户与登录用户不一致");
            }
        }
        
        
        boolean v_AddRet = this.commentService.addComment(i_UserComment);
        if ( v_AddRet )
        {
            $Logger.info("用户（" + i_UserComment.getUserName() + i_UserComment.getUserID() + "）评论：" + i_UserComment.getComment() + "，成功");
            return this.queryComments(i_UserComment);
        }
        else
        {
            $Logger.error("用户（" + i_UserComment.getUserName() + i_UserComment.getUserID() + "）评论：" + i_UserComment.getComment() + "，异常");
            return v_RetResp.setCode("-999").setMessage("系统异常");
        }
    }
    
    
    
    /**
     * 获取用户评论的列表 
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-01-31
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    @RequestMapping(value="list" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<UserComment> queryComments(@RequestBody UserComment i_UserComment)
    {
        BaseResponse<UserComment> v_RetResp = new BaseResponse<UserComment>();
        
        v_RetResp.setData(this.commentService.queryComments(i_UserComment));
        v_RetResp.getData().setRecordCount(this.commentService.queryCommentsCount(i_UserComment));
        v_RetResp.getData().calcPage(i_UserComment);
        
        return v_RetResp;
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
    @RequestMapping(value="goodCountAdd" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<UserComment> goodCountAdd(@RequestParam("token") String i_Token ,@RequestBody UserComment i_UserComment)
    {
        BaseResponse<UserComment> v_RetResp = new BaseResponse<UserComment>();
        
        if ( i_UserComment == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_UserComment.getUserID()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("用户编号为空");
        }
        
        if ( Help.isNull(i_UserComment.getServiceType()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("业务类型为空");
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
            
            if ( !v_User.getUserId().equals(i_UserComment.getUserID()) )
            {
                return v_RetResp.setCode("-902").setMessage("评论用户与登录用户不一致");
            }
        }
        
        
        UserNiceLog v_Log = new UserNiceLog();
        
        v_Log.setNiceID(  i_UserComment.getId());
        v_Log.setNiceType(i_UserComment.getServiceType());
        v_Log.setUserID(  i_UserComment.getUserID());
        
        try
        {
            if ( this.userNiceLogService.goodCountAdd(v_Log) )
            {
                this.commentService.goodCountAdd(i_UserComment);
                
                v_RetResp = this.queryComments(i_UserComment);
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
     * @createDate  2021-01-31
     * @version     v1.0
     *
     * @param i_UserComment
     * @return
     */
    @RequestMapping(value="goodCountSubtract" ,method={RequestMethod.POST} ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<UserComment> goodCountSubtract(@RequestParam("token") String i_Token ,@RequestBody UserComment i_UserComment)
    {
        BaseResponse<UserComment> v_RetResp = new BaseResponse<UserComment>();
        
        if ( i_UserComment == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_UserComment.getUserID()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("用户编号为空");
        }
        
        if ( Help.isNull(i_UserComment.getServiceType()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("业务类型为空");
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
            
            if ( !v_User.getUserId().equals(i_UserComment.getUserID()) )
            {
                return v_RetResp.setCode("-902").setMessage("评论用户与登录用户不一致");
            }
        }
        
        
        UserNiceLog v_Log = new UserNiceLog();
        
        v_Log.setNiceID(  i_UserComment.getId());
        v_Log.setNiceType(i_UserComment.getServiceType());
        v_Log.setUserID(  i_UserComment.getUserID());
        
        try
        {
            if ( this.userNiceLogService.goodCountSubtract(v_Log) )
            {
                this.commentService.goodCountSubtract(i_UserComment);
                
                v_RetResp = this.queryComments(i_UserComment);
                return v_RetResp;
            }
        }
        catch (Exception exce)
        {
            $Logger.error("不能重复点赞" ,exce);
        }
    
        return v_RetResp.setCode("-999").setMessage("异常");
    }
    
}
