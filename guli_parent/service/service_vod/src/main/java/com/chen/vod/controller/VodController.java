package com.chen.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.chem.commonutils.R;
import com.chen.servicebase.exceptionhandler.ChenException;
import com.chen.vod.service.VodService;
import com.chen.vod.utils.AliyunVodSDKUtils;
import com.chen.vod.utils.ConstantVodUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/edu_vod/video")
//@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    //上传阿里云视频
    @PostMapping("/uploadAliyunVideo")
    @ApiOperation(value = "视频上传")
    public R uploadAlyiVideo(MultipartFile file) {
        String videoSourceId = vodService.uploadVideo(file);
        return R.ok().data("videoSourceId", videoSourceId);
    }
    //删除阿里云视频
    @DeleteMapping("/removeAliyunVideoById/{videoId}")
    @ApiOperation(value = "删除单个视频")
    public R removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
                         @PathVariable("videoId") String videoId) {
        vodService.removeVideo(videoId);
        return R.ok().message("视频删除成功");
    }
    //批量删除阿里云视频
    @DeleteMapping("/delete-batch")
    @ApiOperation(value = "批量删除视频")
    public R deleteBatch(@RequestParam List<String> videoIdList) {
        vodService.removeMoreVideo(videoIdList);
        return R.ok();
    }
    //根据视频id获得视频凭证
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request对象中设置视频id
            request.setVideoId(id);
            //调用方法获得凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new ChenException(20001,"视频playAuth获取失败");
        }

    }
}

