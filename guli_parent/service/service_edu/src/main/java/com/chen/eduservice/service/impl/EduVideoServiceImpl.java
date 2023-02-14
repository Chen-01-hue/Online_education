package com.chen.eduservice.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chem.commonutils.R;
import com.chen.eduservice.client.VodClient;
import com.chen.eduservice.entity.EduVideo;
import com.chen.eduservice.mapper.EduVideoMapper;
import com.chen.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.servicebase.exceptionhandler.ChenException;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Chen
 * @since 2022-12-26
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Transactional
    @Override
    public void removeByCourseId(String courseId) {
        //根据课程id查出所有视频的id
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper);
        //封装video_source_id  1,2,3
        List<String> list = eduVideos.stream().filter(eduVideo -> !StringUtils.isEmpty(eduVideo.getVideoSourceId())).map(EduVideo::getVideoSourceId).collect(Collectors.toList());
        //list不为空
        if (list.size() > 0) {
            //删除小节里的所有视频
            R result = vodClient.deleteBatch(list);
            if (result.getCode() == 20001) {
                throw new ChenException(20001, "删除视频失败，熔断器...");
            }
        }
        baseMapper.delete(new QueryWrapper<EduVideo>().eq("course_id",courseId));
    }


}
