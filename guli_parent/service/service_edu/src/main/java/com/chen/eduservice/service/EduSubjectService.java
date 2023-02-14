package com.chen.eduservice.service;

import com.chen.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.eduservice.entity.vo.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Chen
 * @since 2022-12-21
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<OneSubject> getAllOneTwoSubject();
}
