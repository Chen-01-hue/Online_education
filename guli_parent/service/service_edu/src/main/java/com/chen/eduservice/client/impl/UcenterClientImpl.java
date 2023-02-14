package com.chen.eduservice.client.impl;

import com.chem.commonutils.R;
import com.chen.eduservice.client.UcenterClient;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public R getMemberInfo(String token) {
        return R.error().message("解析token出错了");
    }
}
