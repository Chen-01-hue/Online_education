package com.chen.educenter.mapper;

import com.chen.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Chen
 * @since 2023-01-05
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer ucenterMemberService(@Param("day") String day);
}
