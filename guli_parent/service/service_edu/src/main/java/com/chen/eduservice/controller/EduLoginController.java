package com.chen.eduservice.controller;

import com.chem.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin //解决跨域问题
@RequestMapping("/eduservice/user")
public class EduLoginController {

    //login
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("/info")
    public R info(){
        return R.ok().data("roles","admin").data("name","chen").data("avatar","https://img.zcool.cn/community/01639c586c91bba801219c77f6efc8.gif");
    }

    //logout
    @PostMapping("/logout")
    public R logout(){
        return R.ok();
    }
}
