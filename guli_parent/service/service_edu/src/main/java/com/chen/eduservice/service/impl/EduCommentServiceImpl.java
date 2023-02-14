package com.chen.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.eduservice.entity.EduComment;
import com.chen.eduservice.entity.EduTeacher;
import com.chen.eduservice.mapper.EduCommentMapper;
import com.chen.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author Chen
 * @since 2023-01-10
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public Map<String, Object> getCommentByCourseId(Page<EduComment> pageComment, String courseId) {

        baseMapper.selectPage(pageComment,new QueryWrapper<EduComment>().eq("course_id", courseId).orderByDesc("gmt_create"));
        List<EduComment> records = pageComment.getRecords();
        long current = pageComment.getCurrent();
        long pages = pageComment.getPages();
        long size = pageComment.getSize();
        long total = pageComment.getTotal();

        boolean hasNext = pageComment.hasNext();//上一页
        boolean hasPrevious = pageComment.hasPrevious();//下一页

        Map<String, Object> map = new HashMap<>();

        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return  map;


    }
}
