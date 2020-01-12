package com.song.pay.service.impl;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.song.pay.config.BestPayConfig;
import com.song.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author songshiyu
 * @date 2020/1/11 21:13
 **/
@Slf4j
@Service
public class PayServiceImpl implements IPayService{


    @Autowired
    private BestPayService bestPayService;
    /**
     * 此处采用native支付
     * 只需要公众号的appId，商户id，商户的秘钥
     * 自己慕课网的uid：6805711
     * */
    @Override
    public PayResponse create(String orderId, BigDecimal amount) {
        //TODO 写入数据库
        PayRequest request = new PayRequest();
        request.setOrderName("6805711-尝试两下");
        request.setOrderId(orderId);
        request.setOrderAmount(amount.doubleValue());
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_NATIVE);

        PayResponse response = bestPayService.pay(request);
        log.info("response={}",response);
        return response;
    }

    @Override
    public String asyncNotify(String notifyData) {
        //1.签名校验
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info(payResponse.toString());

        //2.金额校验(从数据库查询订单)

        //3.修改订单状态

        //4.告诉微信不要在通知了

        return "<xml>\n" +
                "\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }
}
