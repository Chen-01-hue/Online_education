package com.chen.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.eduservice.entity.EduCourse;
import com.chen.eduservice.entity.EduCourseDescription;
import com.chen.eduservice.entity.EduTeacher;
import com.chen.eduservice.entity.frontVo.CourseQueryVo;
import com.chen.eduservice.entity.frontVo.CourseWebVo;
import com.chen.eduservice.entity.vo.CourseInfoForm;
import com.chen.eduservice.entity.vo.CoursePublishVo;
import com.chen.eduservice.entity.vo.CourseQuery;
import com.chen.eduservice.mapper.EduCourseMapper;
import com.chen.eduservice.service.EduChapterService;
import com.chen.eduservice.service.EduCourseDescriptionService;
import com.chen.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.eduservice.service.EduVideoService;
import com.chen.servicebase.exceptionhandler.ChenException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Chen
 * @since 2022-12-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //课程描述注入
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Resource
    private EduCourseMapper eduCourseMapper;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;

    @Transactional
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        //向课程表里面添加课程基本信息
        //CourseInfoForm对象 转换成 EduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int result = baseMapper.insert(eduCourse);

        if (result<=0){//表示添加失败
            throw new ChenException(20001,"添加课程信息失败");
        }
        //获取添加之后课程信息的id
        String cid = eduCourse.getId();
        //想课程简介表里面添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        //手动设置描述课程表的id，与上面的课程信息表id关联
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);

        return cid;
    }

    @Override
    public CourseInfoForm getCourseInfo(String courseId) {
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,courseInfoForm);

        //查询简介表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoForm.setDescription(courseDescription.getDescription());

        return courseInfoForm;
    }

    @Transactional
    @Override
    public void updateCourseInfo(CourseInfoForm courseInfoForm) {
        //1、修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update <= 0){
            throw new ChenException(20001,"修改课程信息失败");
        }

        //2、修改描述信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescription.setId(courseInfoForm.getId());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        return eduCourseMapper.getPublishCourseInfo(courseId);
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        //取出值，判断他们是否有值
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        //判断条件值是否为空，如果不为空，拼接条件
        //判断是否有传入课程名
        if (!StringUtils.isEmpty(title)){
            //构建条件
            wrapper.like("title",title);//参数1：数据库字段名； 参数2：模糊查询的值
        }
        //判断课程状态
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        //带上门判断后的条件进行分页查询
        baseMapper.selectPage(pageParam, wrapper);

    }

    //删除课程
    @Transactional
    @Override
    public void removeCourse(String courseId) {
        //根据课程id删除小节和视频
        eduVideoService.removeByCourseId(courseId);
        //根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        //根据课程id删除课程描述
        eduCourseDescriptionService.removeById(courseId);
        //根据课程id删除课程本身
        int i = baseMapper.deleteById(courseId);
        if (i == 0) {
            throw new ChenException(20001, "删除失败");
        }
    }

    @Override
    public Map<String, Object> getTeacherInfo(Page<EduCourse> queryVoPage, CourseQueryVo courseQueryVo) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        //判断条件是否为空
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectParentId())){//一级分类
            queryWrapper.eq("subject_parent_id",courseQueryVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectId())){//二级分类
            queryWrapper.eq("subject_id",courseQueryVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getBuyCountSort())) {//销量排序
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseQueryVo.getGmtCreateSort())) {//时间排序
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseQueryVo.getPriceSort())) {//价格排序
            queryWrapper.orderByDesc("price");
        }

        //封装到page里面
        baseMapper.selectPage(queryVoPage, queryWrapper);

        long total = queryVoPage.getTotal();
        List<EduCourse> records = queryVoPage.getRecords();
        long current = queryVoPage.getCurrent();
        long size = queryVoPage.getSize();
        boolean hasNext = queryVoPage.hasNext();
        boolean hasPrevious = queryVoPage.hasPrevious();
        long pages = queryVoPage.getPages();

        HashMap<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

}
