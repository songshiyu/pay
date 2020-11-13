package com.song.pay.service;

import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

/**
 * @description:
 * @create: 2020/11/11 08:27:37
 **/
public interface IPayService {

    /**
     * 创建/发起支付
     */
    PayResponse create(String orderId, BigDecimal amount);

    /**
     * 接收异步通知
     */
    void asyncNotify(String asyncNotify);
}
