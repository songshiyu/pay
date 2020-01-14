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
        //比较严重(正常情况下是不会发生的)，建议发出告警
        PayInfo payInfo = payInfoMapper.selectByOrderNo(Long.parseLong(payResponse.getOrderId()));
        if (payInfo == null){
            //TODO 发出告警
            throw new RuntimeException("通过OrderNo查询到的结果是null,orderNo = " + payResponse.getOrderId());
        }
        //如果订单支付状态不是"已支付"
        if (!payInfo.getPlatformStatus().equals(OrderStatusEnum.REVOKED)){
            //Double类型比较大小，精度问题不好控制。
            if (payInfo.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount())) != 0){
                //TODO 发出告警
                throw new RuntimeException("异步通知中的金额和数据库中的不一致,orderNo= " + payResponse.getOrderId());
            }
        }
        //3.修改订单支付状态
        payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());

        //交易流水号
        payInfo.setPlatformNumber(payResponse.getOutTradeNo());

        payInfo.setUpdateTime(null);
        payInfoMapper.updateByPrimaryKeySelective(payInfo);

        //TODO pay发送MQ消息 mall接受MQ消息

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

    @Override
    public PayInfo queryByOrderId(String orderId) {
        return payInfoMapper.selectByOrderNo(Long.parseLong(orderId));
    }
}
