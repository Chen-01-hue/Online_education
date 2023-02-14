package com.chen.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chem.commonutils.R;
import com.chen.eduservice.entity.EduCourse;
import com.chen.eduservice.entity.vo.CourseInfoForm;
import com.chen.eduservice.entity.vo.CoursePublishVo;
import com.chen.eduservice.entity.vo.CourseQuery;
import com.chen.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Chen
 * @since 2022-12-26
 */
@RestController
@RequestMapping("/eduservice/edu-course")
//@CrossOrigin //解决跨域问题
@Api(description = "课程管理")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //添加课程基本信息方法
    @PostMapping("/addCourseInfo")
    @ApiOperation(value = "添加课程基本信息")
    public R addCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        //返回添加之后课程id，为了后面添加大纲使用
        String id = eduCourseService.saveCourseInfo(courseInfoForm);
        return R.ok().data("courseId",id);
    }

    //根据课程id查询课程基本信息
    @GetMapping("/getCourseInfoById/{courseId}")
    @ApiOperation(value = "根据课程id查询课程基本信息")
    public R getCourseInfoById(@PathVariable String courseId){
        CourseInfoForm courseInfoForm = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoForm",courseInfoForm);
    }

    //修改课程信息
    @PostMapping("/updateCourseInfo")
    @ApiOperation(value = "修改课程信息")
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        eduCourseService.updateCourseInfo(courseInfoForm);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("/getpublishCourseInfo/{id}")
    @ApiOperation(value = "查询课程确认信息")
    public R getpublishCourseInfo(@PathVariable String id){
        CoursePublishVo publishCourseInfo = eduCourseService.getPublishCourseInfo(id);
        return R.ok().data("publishCourse",publishCourseInfo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    @ApiOperation(value = "修改课程状态")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setStatus("Normal"); //设置课程发布状态
        eduCourse.setId(id);
        boolean flag = eduCourseService.updateById(eduCourse);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    //课程列表数据查询
    @GetMapping("/getCourseList")
    @ApiOperation(value = "课程列表数据查询")
    public R getCourseList() {
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list", list);
    }

    //根据id删除课程
    @DeleteMapping("/removeCourseById/{courseId}")
    @ApiOperation(value = "根据id删除课程")
    public R removeCourse(@PathVariable("courseId") String courseId) {
        eduCourseService.removeCourse(courseId);
        return R.ok();
    }
    //多条件查询讲师带分页
    @ApiOperation(value = "多条件查询课程带分页")
    @PostMapping("/pageCourseCondition/{page}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "page", value = "当前页码", required = true)@PathVariable Long page,
                                  @ApiParam(name = "limit", value = "每页记录数", required = true)@PathVariable Long limit,
                                  @RequestBody(required = false) CourseQuery courseQuery){//通过封装CourseQuery对象来直接传递查询条件
        //创建分页page对象
        Page<EduCourse> pageParam = new Page<>(page, limit);

        //调用方法实现多条件分页查询
        eduCourseService.pageQuery(pageParam,courseQuery);

        //获取查询到的数据
        List<EduCourse> records = pageParam.getRecords();

        //获取总记录数
        long total = pageParam.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }


}


