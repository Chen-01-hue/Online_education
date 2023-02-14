package com.chen.educenter.controller;


import com.chem.commonutils.JwtUtils;
import com.chem.commonutils.R;
import com.chem.commonutils.user.UcenterMemberOrder;
import com.chen.educenter.entity.UcenterMember;
import com.chen.educenter.entity.vo.RegisterVo;
import com.chen.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Chen
 * @since 2023-01-05
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    //电话和密码登录,生成token
    @PostMapping("/login")
    public R login(@RequestBody UcenterMember ucenterMember){
        String token =ucenterMemberService.login(ucenterMember);
        return R.ok().data("token",token);
    }

    //电话、密码、验证码、昵称进行注册
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return R.ok();
    }
    //通过request请求，解析token获取用户id
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用jwt工具类，获取头部信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (memberId.equals("")){
            return R.error();
        }
        //查询数据库根据id获得用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }
    //根据用户id获取客户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id){
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        //把UcenterMember复制为UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(ucenterMember,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable("day") String day){
        Integer count = ucenterMemberService.ucenterMemberService(day);
        return R.ok().data("countRegister",count);
    }
}

