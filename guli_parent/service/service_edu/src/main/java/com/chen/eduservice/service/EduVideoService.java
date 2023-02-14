package com.chen.eduservice.service;

import com.chen.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Chen
 * @since 2022-12-26
 */
public interface EduVideoService extends IService<EduVideo> {
    //删除小节和视频
    void removeByCourseId(String courseId);

}
