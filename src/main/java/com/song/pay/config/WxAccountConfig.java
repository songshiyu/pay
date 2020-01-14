package com.song.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author songshiyu
 * @date 2020/1/14 22:24
 **/
@Component
@ConfigurationProperties(prefix = "wx")
@Data
public class WxAccountConfig {

    private String appid;
    private String mchId;
    private String mchKey;
    private String notifyUrl;
    private String returnUrl;
}
