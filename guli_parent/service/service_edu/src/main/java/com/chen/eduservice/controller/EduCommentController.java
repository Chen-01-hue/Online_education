package com.chen.eduservice.controller;


import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chem.commonutils.R;
import com.chem.commonutils.ResultCode;
import com.chem.commonutils.to.UcenterMemberTo;
import com.chen.eduservice.client.UcenterClient;
import com.chen.eduservice.entity.EduComment;
import com.chen.eduservice.service.EduCommentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author Chen
 * @since 2023-01-10
 */
@RestController
@RequestMapping("/eduservice/edu-comment")
@Api(description = "评论管理")
//@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService eduCommentService;

    @Autowired
    private UcenterClient ucenterClient;
    //获取对应课程的所有评论
    @GetMapping("/getCommentsByCourseId/{page}/{limit}/{courseId}")
    public R getCommentByCourseId(@PathVariable long page, @PathVariable long limit,@PathVariable String courseId){
        Page<EduComment> pageComment = new Page<>(page,limit);
        Map<String,Object> map = eduCommentService.getCommentByCourseId(pageComment,courseId);
        return R.ok().data(map);
    }
    //发布评论
    @PostMapping("/postComments")
    public R postComments(HttpServletRequest request,@RequestBody EduComment eduComment){
        //调用远程服务：通过request请求，解析token获取用户id
        R r = ucenterClient.getMemberInfo(request.getHeader("token"));
        if (r.getCode() == 20000){
            //UcenterMemberTo ucenterMemberTo = r.getData("userInfo", new TypeReference<UcenterMemberTo>() {});
            UcenterMemberTo ucenterMemberTo = (UcenterMemberTo)r.getData().get("userInfo");
            eduComment.setMemberId(ucenterMemberTo.getId());
            eduComment.setAvatar(ucenterMemberTo.getAvatar());
            eduComment.setNickname(ucenterMemberTo.getNickname());
            eduCommentService.save(eduComment);
            return R.ok();
        }else {
            return R.error();
        }
    }

}

