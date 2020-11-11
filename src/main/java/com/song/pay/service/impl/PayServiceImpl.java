package com.song.pay.service.impl;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.song.pay.service.IPayService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @description:
 * @create: 2020/11/11 08:28:44
 **/
@Service
public class PayServiceImpl implements IPayService {

    @Override
    public PayResponse create(String orderId, BigDecimal amount) {

        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId("wxd898fcb01713c658");
        wxPayConfig.setMchId("1483469312");
        wxPayConfig.setMchKey("098F6BCD4621D373CADE4E832627B4F6");
        wxPayConfig.setNotifyUrl("http://www.baidu.com");

        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);

        PayRequest payRequest = new PayRequest();
        payRequest.setOrderId(orderId);
        payRequest.setOrderAmount(amount.doubleValue());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_NATIVE);
        payRequest.setOrderName("测试支付");

        PayResponse payResponse = bestPayService.pay(payRequest);
        return payResponse;
    }
}
