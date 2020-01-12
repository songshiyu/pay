package com.song.pay.service;

import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

/**
 * @author songshiyu
 * @date 2020/1/11 21:12
 **/
public interface IPayService {

    /**
     * 创建支付/发起支付
     * */
    PayResponse create(String orderId, BigDecimal amount);

    /**
     * 接受支付的异步通知
     * @param notifyData
     * */
    String asyncNotify(String notifyData);
}
