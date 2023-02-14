package com.chen.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.eduservice.entity.EduTeacher;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author Chen
 * @since 2023-01-10
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String, Object> getCommentByCourseId(Page<EduComment> pageTeacher, String courseId);
}
