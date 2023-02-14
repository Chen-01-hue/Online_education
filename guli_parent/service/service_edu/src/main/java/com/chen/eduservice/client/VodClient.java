package com.chen.eduservice.client;

import com.chem.commonutils.R;
import com.chen.eduservice.client.impl.VodClientImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodClientImpl.class)
@Component
public interface VodClient {
    //删除阿里云视频
    @DeleteMapping("/edu_vod/video/removeAliyunVideoById/{videoId}")
    @ApiOperation(value = "删除视频")
    public R removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
                         @PathVariable("videoId") String videoId);

    //批量删除阿里云视频
    @DeleteMapping("/edu_vod/video/delete-batch")
    @ApiOperation(value = "批量删除视频")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
