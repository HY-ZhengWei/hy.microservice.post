package org.hy.microservice.post.userNice;

import org.hy.common.Help;
import org.hy.common.app.Param;
import org.hy.common.xml.log.Logger;
import org.hy.microservice.common.BaseResponse;
import org.hy.microservice.post.PostController;
import org.hy.microservice.post.user.UserSSO;
import org.hy.microservice.post.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;





/**
 * 用户点赞的控制层
 *
 * @author      LHao
 * @createDate  2021-02-07
 * @version     v1.0
 */
@Controller
@RestController
@RequestMapping("userNice")
public class UserNiceLogController 
{

    private static final Logger $Logger = new Logger(PostController.class);
    
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
     * 更新点赞（加）
     *
     * @author      ZhengWei(HY)
     * @createDate  2020-08-18
     * @version     v1.0
     * 
     * @param i_Token        票据号
     * @param i_UserNiceLog  点赞信息
     * @return
     */
    @PostMapping(value="goodCountAdd")
    public BaseResponse<UserNiceLog> goodCountAdd(@RequestParam("token") String i_Token ,@RequestBody UserNiceLog i_UserNiceLog)
    {
        BaseResponse<UserNiceLog> v_RetResp = new BaseResponse<UserNiceLog>();
        
        if ( i_UserNiceLog == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_UserNiceLog.getNiceID()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("点赞对象的编号为空");
        }
        
        if ( Help.isNull(i_UserNiceLog.getUserID()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("用户编号为空");
        }
        
        if ( Help.isNull(i_UserNiceLog.getServiceType()) ) 
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
            
            if ( !v_User.getUserId().equals(i_UserNiceLog.getUserID()) )
            {
                return v_RetResp.setCode("-902").setMessage("发贴用户与登录用户不一致");
            }
        }
        
        
        i_UserNiceLog.setNiceType(i_UserNiceLog.getServiceType());
        
        try
        {
            if ( this.userNiceLogService.goodCountAdd(i_UserNiceLog) )
            {
                i_UserNiceLog.setTotalCount(this.userNiceLogService.queryNiceCount(i_UserNiceLog));
                return v_RetResp.setData(i_UserNiceLog);
            }
        }
        catch (Exception exce)
        {
            $Logger.error("不能重复点赞" ,exce);
        }
        
        return v_RetResp.setCode("-999").setMessage("异常");
    }



    /**
     * 更新点赞（减）
     *
     * @author      ZhengWei(HY)
     * @createDate  2020-08-18
     * @version     v1.0
     *
     * @param i_UserNiceLog
     * @return
     */
    @PostMapping(value="goodCountSubtract")
    public BaseResponse<UserNiceLog> goodCountSubtract(@RequestParam("token") String i_Token ,@RequestBody UserNiceLog i_UserNiceLog)
    {
        BaseResponse<UserNiceLog> v_RetResp = new BaseResponse<UserNiceLog>();
        
        if ( i_UserNiceLog == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        if ( Help.isNull(i_UserNiceLog.getNiceID()) ) 
        {
            return v_RetResp.setCode("-2").setMessage("点赞对象的编号为空");
        }
        
        if ( Help.isNull(i_UserNiceLog.getUserID()) ) 
        {
            return v_RetResp.setCode("-3").setMessage("用户编号为空");
        }
        
        if ( Help.isNull(i_UserNiceLog.getServiceType()) ) 
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
            
            if ( !v_User.getUserId().equals(i_UserNiceLog.getUserID()) )
            {
                return v_RetResp.setCode("-902").setMessage("发贴用户与登录用户不一致");
            }
        }
        
        
        i_UserNiceLog.setNiceType(i_UserNiceLog.getServiceType());
        
        try
        {
            if ( this.userNiceLogService.goodCountSubtract(i_UserNiceLog) )
            {
                i_UserNiceLog.setTotalCount(this.userNiceLogService.queryNiceCount(i_UserNiceLog));
                return v_RetResp.setData(i_UserNiceLog);
            }
        }
        catch (Exception exce)
        {
            $Logger.error("不能重复点赞" ,exce);
        }
        
        return v_RetResp.setCode("-999").setMessage("异常");
    }
    
    
    
    /**
     * 点赞列表查询
     *
     * @author      ZhengWei(HY)
     * @createDate  2021-02-08
     * @version     v1.0
     *
     * @param i_UserNiceLog
     * @return
     */
    @PostMapping(value="list")
    public BaseResponse<UserNiceLog> queryList(@RequestBody UserNiceLog i_UserNiceLog)
    {
        BaseResponse<UserNiceLog> v_RetResp = new BaseResponse<UserNiceLog>();
        
        if ( i_UserNiceLog == null )
        {
            return v_RetResp.setCode("-1").setMessage("未收到任何参数");
        }
        
        
        i_UserNiceLog.setNiceType(i_UserNiceLog.getServiceType());
        
        try
        {
            v_RetResp.setData(this.userNiceLogService.queryNices(i_UserNiceLog));
            v_RetResp.getData().setRecordCount(this.userNiceLogService.queryNicesCount(i_UserNiceLog));
            
            return v_RetResp;
        }
        catch (Exception exce)
        {
            $Logger.error(exce);
        }
        
        return v_RetResp.setCode("-999").setMessage("异常");
    }
    
}
