package com.chen.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chem.commonutils.R;
import com.chen.eduservice.entity.EduCourse;
import com.chen.eduservice.entity.EduTeacher;
import com.chen.eduservice.service.EduCourseService;
import com.chen.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("eduservice/teacherfront")
//@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;
    //分页查询讲师
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable("page") long page, @PathVariable("limit") long limit){
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = eduTeacherService.getTeacherFrontList(pageTeacher);
        return R.ok().data(map);
    }

    //讲师查询的功能
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable("teacherId") String teacherId){
        //1、查询讲师基本信息
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);

        //1、查询讲师课程的基本信息
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",teacherId);
        //讲师可能一个课程也可能多个课程
        List<EduCourse> list = eduCourseService.list(queryWrapper);

        return R.ok().data("teacher",eduTeacher).data("courseList",list);
    }
}
