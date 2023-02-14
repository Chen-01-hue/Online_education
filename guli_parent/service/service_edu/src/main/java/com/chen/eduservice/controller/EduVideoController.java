package com.chen.eduservice.controller;


import com.chem.commonutils.R;
import com.chen.eduservice.client.VodClient;
import com.chen.eduservice.entity.EduVideo;
import com.chen.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Chen
 * @since 2022-12-26
 */
@RestController
@RequestMapping("/eduservice/edu-video")
//@CrossOrigin //解决跨域问题
@Api(description = "小节管理")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("/addVideo")
    @ApiOperation(value = "添加小节")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }


    //删除小节
    // TODO 后面这个方法需要完善，删除小节的时候，同时也要把视频删除
    @DeleteMapping("/deleteVideo/{id}")
    @ApiOperation(value = "删除小节")
    public R deleteVideo(@PathVariable("id") String id){
        //根据小节id查询出视频id，进行删除
        EduVideo eduVideobyId = eduVideoService.getById(id);
        String videoSourceId = eduVideobyId.getVideoSourceId();
        //判断是否有视频,有就删除
        if (!StringUtils.isEmpty(videoSourceId)) {
            //远程调用vod删除视频
            vodClient.removeVideo(videoSourceId);
        }
        //删除小节
        eduVideoService.removeById(id);
        return R.ok();
    }

    //修改小节
    @PostMapping("/updateVideo")
    @ApiOperation(value = "修改小节")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    //根据小节id查询
    @GetMapping("/getVideoById/{videoId}")
    @ApiOperation(value = "根据小节id查询")
    public R getVideoById(@PathVariable String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("video",eduVideo);
    }

}


