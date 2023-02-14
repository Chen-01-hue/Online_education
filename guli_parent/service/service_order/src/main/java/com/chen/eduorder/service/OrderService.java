package com.chen.eduorder.service;

import com.chen.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Chen
 * @since 2023-01-15
 */
public interface OrderService extends IService<Order> {

    String createOrders(String courseId, String memberId);
}
