package com.song.pay.service.impl;

import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.song.pay.dao.PayInfoMapper;
import com.song.pay.enums.PayPlatformEnum;
import com.song.pay.pojo.PayInfo;
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

    @Autowired
    private PayInfoMapper payInfoMapper;

    /**
     * 此处采用native支付和支付宝pc支付
     * 自己慕课网的uid：6805711
     * */
    @Override
    public PayResponse create(String orderId, BigDecimal amount,BestPayTypeEnum bestPayTypeEnum) {
        if (bestPayTypeEnum != BestPayTypeEnum.WXPAY_NATIVE
                && bestPayTypeEnum != BestPayTypeEnum.ALIPAY_PC){
            throw new RuntimeException("暂不支持的支付方式");
        }

        PayPlatformEnum payTypeEnum = PayPlatformEnum.getByBestPayTypeEnum(bestPayTypeEnum);
        PayInfo payInfo = new PayInfo(Long.parseLong(orderId),payTypeEnum.getCode(), OrderStatusEnum.NOTPAY.name(),amount);
        payInfoMapper.insertSelective(payInfo);

        PayRequest request = new PayRequest();
        request.setOrderName("6805711-尝试两下");
        request.setOrderId(orderId);
        request.setOrderAmount(amount.doubleValue());
        request.setPayTypeEnum(bestPayTypeEnum);

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
        if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.WX){
            return "<xml>\n" +
                    "\n" +
                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>";
        }else if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.ALIPAY){
            return "success";
        }
        throw new RuntimeException("异步通知中错误的支付平台");
    }
}
