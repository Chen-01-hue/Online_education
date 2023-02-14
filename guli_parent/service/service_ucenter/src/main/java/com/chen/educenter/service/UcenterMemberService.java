package com.chen.educenter.service;

import com.chen.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Chen
 * @since 2023-01-05
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);

    UcenterMember getMenberByOperid(String openid);

    Integer ucenterMemberService(String day);
}
