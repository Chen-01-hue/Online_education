package com.chen.eduservice.mapper;

import com.chen.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.eduservice.entity.frontVo.CourseWebVo;
import com.chen.eduservice.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Chen
 * @since 2022-12-26
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVo getPublishCourseInfo(@Param("courseId") String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}

