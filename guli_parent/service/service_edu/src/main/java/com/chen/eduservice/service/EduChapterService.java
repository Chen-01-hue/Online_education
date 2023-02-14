package com.chen.eduservice.service;

import com.chen.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Chen
 * @since 2022-12-26
 */
public interface EduChapterService extends IService<EduChapter> {

    //获取课程大纲列表，根据课程id进行查询
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);
    //根据课程id删除章节
    void removeChapterByCourseId(String courseId);
}
