package com.chen.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.eduservice.entity.vo.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Chen
 * @since 2022-11-11
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //多条件查询讲师带分页
    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
