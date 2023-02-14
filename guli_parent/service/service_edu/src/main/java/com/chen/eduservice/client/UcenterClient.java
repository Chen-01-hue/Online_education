package com.chen.eduservice.client;

import com.chem.commonutils.R;
import com.chen.eduservice.client.impl.UcenterClientImpl;
import com.chen.eduservice.client.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
@Component
public interface UcenterClient {
    //通过request请求，解析token获取用户id

    /**
     * 解决feign不能传HttpServletRequest
     * 1.通过 @RequestHeader(name = “headerName”) 来传递
     * @param token
     * @return
     */
    @GetMapping("/educenter/member/getMemberInfo")
    public R getMemberInfo(@RequestHeader("token") String token);
}
