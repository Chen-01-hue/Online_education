package com.chen.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.eduservice.entity.EduChapter;
import com.chen.eduservice.entity.EduVideo;
import com.chen.eduservice.entity.chapter.ChapterVo;
import com.chen.eduservice.entity.chapter.VideoVo;
import com.chen.eduservice.mapper.EduChapterMapper;
import com.chen.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.eduservice.service.EduVideoService;
import com.chen.servicebase.exceptionhandler.ChenException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Chen
 * @since 2022-12-26
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService eduVideoService;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //查询章节信息
        List<EduChapter> eduChapters = baseMapper.selectList(new QueryWrapper<EduChapter>().eq("course_id", courseId));
        //查询小节信息
        List<EduVideo> eduVideos = eduVideoService.list(new QueryWrapper<EduVideo>().eq("course_id", courseId));
        //最终要的数据列表
        List<ChapterVo> finalChapterVos = eduChapters.stream().map(eduChapter -> {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            List<VideoVo> finalVideoVos = eduVideos.stream().filter(eduVideo -> eduVideo.getChapterId().equals(eduChapter.getId())).map(eduVideo -> {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo,videoVo);
                return videoVo;
            }).collect(Collectors.toList());
            chapterVo.setChildren(finalVideoVos);
            return chapterVo;
        }).collect(Collectors.toList());
        return finalChapterVos;
    }

    //删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapter章节id 查询查询小节表，如果查询有数据，则不删除
        int count = eduVideoService.count(new QueryWrapper<EduVideo>().eq("chapter_id",chapterId));
        //判断
        if (count>0){
            //能查询出来小节，不进行删除
            throw new ChenException(20001,"还有小节数据，不能删除");
        }else {
            //不能查询出小节，进行删除
            int delete = baseMapper.deleteById(chapterId);
            return delete>0;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        baseMapper.delete(new QueryWrapper<EduChapter>().eq("course_id",courseId));
    }

}
