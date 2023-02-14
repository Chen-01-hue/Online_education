package com.chen.eduorder.service;

import com.chen.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author Chen
 * @since 2023-01-15
 */
public interface PayLogService extends IService<PayLog> {
    //返回信息，包含二维码地址，还有其他需要的信息
    Map createNative(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);

}
