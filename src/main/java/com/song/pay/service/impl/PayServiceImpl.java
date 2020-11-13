package com.song.pay.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.song.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @description:
 * @create: 2020/11/11 08:28:44
 **/
@Service
@Slf4j
public class PayServiceImpl implements IPayService {

    @Autowired
    private BestPayService bestPayService;

    @Override
    public PayResponse create(String orderId, BigDecimal amount) {

        PayRequest payRequest = new PayRequest();
        payRequest.setOrderId(orderId);
        payRequest.setOrderAmount(amount.doubleValue());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_NATIVE);
        payRequest.setOrderName("测试支付");

        PayResponse payResponse = bestPayService.pay(payRequest);
        return payResponse;
    }

    @Override
    public void asyncNotify(String asyncNotify) {
        PayResponse payResponse = bestPayService.asyncNotify(asyncNotify);
        log.info("payResponse={}", payResponse);
    }
}
