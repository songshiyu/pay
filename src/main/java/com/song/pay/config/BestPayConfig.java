package com.song.pay.config;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author songshiyu
 * @date 2020/1/12 21:23
 **/
@Component
public class BestPayConfig {


    /**
     *  项目启动是时就会执行此段代码，因为@Bean注解
     */
    @Bean
    public BestPayService bestPayService(){
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId("wxd898fcb01713c658");
        wxPayConfig.setMchId("1483469312");
        wxPayConfig.setMchKey("098F6BCD4621D373CADE4E832627B4F6");
        //接受支付平台异步通知的地址
        wxPayConfig.setNotifyUrl("http://lxk-song.natapp1.cc/notify");

        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);
        return bestPayService;
    }
}
