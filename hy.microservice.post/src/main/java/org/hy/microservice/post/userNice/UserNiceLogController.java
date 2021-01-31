package org.hy.microservice.post.userNice;

import org.hy.microservice.post.PostInfo;
import org.hy.microservice.post.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;





/**
 * @author LHao
 * @version [v1.0]
 * @ClassName: UserNiceLogController
 * @Description: 用户点赞通用类
 * @date 2020/10/15 21:13
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RestController
@RequestMapping("userNice")
public class UserNiceLogController 
{

    @Autowired
    public UserNiceLogService userNiceLogService;

    

    /**
     * 更新点赞（加）
     *
     * @author      ZhengWei(HY)
     * @createDate  2020-08-18
     * @version     v1.0
     *
     * @param i_UserNiceLog
     * @return
     */
    @PostMapping(value="goodCountAdd")
    public BaseResponse<PostInfo> goodCountAdd(@RequestBody UserNiceLog i_UserNiceLog, HttpServletRequest request)
    {
        BaseResponse<PostInfo> v_Ret = new BaseResponse<PostInfo>();
        
        boolean resultFlag = this.userNiceLogService.goodCountAdd(i_UserNiceLog);
        if ( !resultFlag )
        {
            v_Ret.setCode("-999").setMessage("点赞异常");
        }
        
        return v_Ret;
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
    public BaseResponse<PostInfo> goodCountSubtract(@RequestBody UserNiceLog i_UserNiceLog)
    {
        BaseResponse<PostInfo> v_Ret = new BaseResponse<PostInfo>();

        boolean resultFlag = this.userNiceLogService.goodCountSubtract(i_UserNiceLog);
        if ( !resultFlag )
        {
            v_Ret.setCode("-999").setMessage("取消点赞异常");
        }
        
        return v_Ret;
    }
    
}
