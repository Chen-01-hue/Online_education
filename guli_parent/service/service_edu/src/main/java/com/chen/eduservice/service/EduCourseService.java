package com.chen.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.eduservice.entity.frontVo.CourseQueryVo;
import com.chen.eduservice.entity.frontVo.CourseWebVo;
import com.chen.eduservice.entity.vo.CourseInfoForm;
import com.chen.eduservice.entity.vo.CoursePublishVo;
import com.chen.eduservice.entity.vo.CourseQuery;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Chen
 * @since 2022-12-26
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程基本信息方法
    String saveCourseInfo(CourseInfoForm courseInfoForm);
    //根据课程id查询课程基本信息
    CourseInfoForm getCourseInfo(String courseId);
    //修改课程信息
    void updateCourseInfo(CourseInfoForm courseInfoForm);
    //根据课程id查询课程确认信息
    public CoursePublishVo getPublishCourseInfo(String courseId);
    //实现多条件分页查询
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);
    //删除课程
    void removeCourse(String courseId);

    Map<String, Object> getTeacherInfo(Page<EduCourse> queryVoPage, CourseQueryVo courseQueryVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
