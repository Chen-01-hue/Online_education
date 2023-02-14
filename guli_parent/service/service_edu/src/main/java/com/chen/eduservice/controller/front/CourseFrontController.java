package com.chen.eduservice.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chem.commonutils.JwtUtils;
import com.chem.commonutils.R;
import com.chem.commonutils.user.CourseWebVoOrder;
import com.chen.eduservice.client.OrderClient;
import com.chen.eduservice.entity.EduCourse;
import com.chen.eduservice.entity.chapter.ChapterVo;
import com.chen.eduservice.entity.frontVo.CourseQueryVo;
import com.chen.eduservice.entity.frontVo.CourseWebVo;
import com.chen.eduservice.service.EduChapterService;
import com.chen.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("eduservice/coursefront")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;

//    @Autowired
////    private OrderClient orderClient;

    @PostMapping("getFrontInfo/{page}/{limit}")//@RequestBody(required = false)传入值可以为空
    public R getFrontInfo(@PathVariable long page, @PathVariable long limit,
                          @RequestBody(required = false) CourseQueryVo courseQueryVo){

        Page<EduCourse> queryVoPage = new Page<>(page,limit);

        Map<String,Object> map = eduCourseService.getTeacherInfo(queryVoPage,courseQueryVo);
        return R.ok().data(map);
    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId,HttpServletRequest request){
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);

        //根据课程id，查询章节和小节
        List<ChapterVo> chapterVoList = eduChapterService.getChapterVideoByCourseId(courseId);

        //根据课程Id和用户ID查询订单表中是否支付
        String memberId = JwtUtils.getMemberIdByJwtToken(request);//取出用户id
        boolean buyCourse = false;
        if(!StringUtils.isEmpty(memberId)){
             buyCourse = orderClient.isBuyCourse(courseId, memberId);
        }
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVoList).data("isBuy",buyCourse);
    }

    //远程调用根据课程id获得课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id){
        CourseWebVo courseInfo = eduCourseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }

}
